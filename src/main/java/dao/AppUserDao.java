package dao;

import models.AppUser;

import java.util.HashSet;
import java.util.Optional;

public interface AppUserDao {

    HashSet<AppUser> getAll();

    void saveUser(AppUser user);

    void deleteUser(AppUser user);

    Optional<AppUser> getUserById(Long id);

    Optional<AppUser> getUserByEmail(String email);

    Optional<AppUser> getUserByLogin(String login);

    HashSet<AppUser> getFollowedUsers(AppUser loggedUser);

    HashSet<AppUser> getNotFollowedUsers(AppUser loggedUser);

    HashSet<AppUser> getFollowers(AppUser loggedUser);

    void follow(AppUser logged, AppUser userToFollow);

    void unfollow(AppUser logged, AppUser userToFollow);
}
