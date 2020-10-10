package services;

import errors.ValidationError;
import models.AppUser;

import java.util.List;

public interface TweetAppService {

    List<ValidationError> validateUser(String login, String email);

    void registerUser(AppUser user);

    boolean isLoginAndPasswordValid(String login, String password);
}
