package dao.impl;

import models.AppUser;
import dao.AbstractMySQLDao;
import dao.AppUserDao;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

public class MySQLUserDao extends AbstractMySQLDao implements AppUserDao {
    @Override
    public HashSet<AppUser> getAll() {
        TypedQuery<AppUser> getAll = em.createQuery("from AppUser u where u.isActive = true", AppUser.class);
        List<AppUser> resultList = getAll.getResultList();
        return new HashSet<>(resultList);
    }

    @Override
    public void saveUser(AppUser user) {
        hibernateUtil.save(user);
    }

    @Override
    public void deleteUser(AppUser user) {
        //unfollowBeforeDelete(user);
        //hibernateUtil.delete(AppUser.class, user.getId());
        user.setActive(false);
    }

    @Override
    public Optional<AppUser> getUserById(Long id) {
        TypedQuery<AppUser> query = em.createQuery("select u from AppUser u where u.id =:id", AppUser.class);
        query.setParameter("id", id);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AppUser> getUserByEmail(String email) {
        TypedQuery<AppUser> query = em.createQuery("select u from AppUser u where u.email =:email", AppUser.class);
        query.setParameter("email", email);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AppUser> getUserByLogin(String login) {
        TypedQuery<AppUser> query = em.createQuery("select u from AppUser u where u.login =:login", AppUser.class);
        query.setParameter("login", login);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public HashSet<AppUser> getFollowedUsers(AppUser loggedUser) {
        return new HashSet<>(loggedUser.getFollowing());
    }

    @Override
    public HashSet<AppUser> getNotFollowedUsers(AppUser loggedUser) {
        Query query = em.createQuery("select u from AppUser u where u.login != :login");
        query.setParameter("login", loggedUser.getLogin());
        HashSet<AppUser> appUsers = new HashSet<AppUser>(query.getResultList());
        appUsers.removeAll(loggedUser.getFollowing());
        return appUsers;
    }

    @Override
    public HashSet<AppUser> getFollowers(AppUser loggedUser) {
        Query query = em.createQuery("select followers from AppUser u where u.id = :userId");
        query.setParameter("userId", loggedUser.getId());
        ArrayList<AppUser> followers = new ArrayList<>(query.getResultList());
        return followers.stream().filter(user -> user.isActive()).collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public void follow(AppUser loggedUser, AppUser userToFollow) {
        loggedUser.getFollowing().add(userToFollow);
        saveUser(loggedUser);
    }

    @Override
    public void unfollow(AppUser loggedUser, AppUser userToStopFollow) {
        loggedUser.getFollowing().remove(userToStopFollow);
        saveUser(loggedUser);
    }

    private void unfollowBeforeDelete(AppUser user) {
        getFollowers(user).forEach(follower -> unfollow(follower, user));
    }
}
