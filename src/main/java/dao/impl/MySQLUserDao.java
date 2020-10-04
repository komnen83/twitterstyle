package dao.impl;

import dao.AbstractMySQLDao;
import dao.AppUserDao;
import models.AppUser;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class MySQLUserDao extends AbstractMySQLDao implements AppUserDao {

    @Override
    public HashSet<AppUser> getAll() {
        TypedQuery<AppUser> getAll = em.createQuery("select u from AppUser u where u.isActive = true ", AppUser.class);
        List<AppUser> resultList = getAll.getResultList();
        return new HashSet<>(resultList);
    }

    @Override
    public void saveUser(AppUser user) {
        hibernateUtil.save(user);
    }

    @Override
    public void deleteUser(AppUser user) {
//        unfollowBeforeDelete(user);
//        hibernateUtil.delete(AppUser.class, user.getIsActive());
        user.setIsActive(false);
    }

    @Override
    public AppUser getUserById(Long id) {
        TypedQuery<AppUser> query = em.createQuery("select u from AppUser u where u.id=:id ", AppUser.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public AppUser getUserByEmail(String email) {
        TypedQuery<AppUser> query = em.createQuery("select u from AppUser u where u.email=:email", AppUser.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public AppUser getUserByLogin(String login) {
        TypedQuery<AppUser> query = em.createQuery("select u from AppUser u where u.login=:login", AppUser.class);
        query.setParameter("login", login);
        return query.getSingleResult();
    }

    @Override
    public HashSet<AppUser> getFollowedUsers(AppUser loggedUser) {
        return new HashSet<>(loggedUser.getFollowing());
    }

    @Override
    public HashSet<AppUser> getNotFollowedUser(AppUser loggedUser) {
        Query query = em.createQuery("select u from AppUser u where u not in :followed and u.isActive = true", AppUser.class);
        query.setParameter("followed", new HashSet(loggedUser.getFollowing()));
        return new HashSet(query.getResultList());
    }

    @Override
    public HashSet<AppUser> getFollowers(AppUser loggedUser) {
        TypedQuery<AppUser> query = em.createQuery("select followers from AppUser u where u.id = :userID", AppUser.class);
        query.setParameter("userID", loggedUser.getId());
        return new HashSet<>(query.getResultList().stream().filter(o -> o.isActive()).collect(Collectors.toSet()));
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
