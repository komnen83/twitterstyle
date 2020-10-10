package services.impl;

import dao.AppUserDao;
import dao.TweetDao;
import errors.ValidationError;
import models.AppUser;
import org.apache.commons.codec.digest.DigestUtils;
import services.TweetAppService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static utils.ServletUtils.*;

public class TweetAppServiceImpl implements TweetAppService {


    private AppUserDao appUserDao;
    private TweetDao tweetDao;

    public TweetAppServiceImpl(AppUserDao appUserDao, TweetDao tweetDao) {
        this.appUserDao = appUserDao;
        this.tweetDao = tweetDao;
    }

    private boolean isUserLoginInUse(String userLogin) {
        return appUserDao.getUserByLogin(userLogin).isPresent();
    }

    private boolean isUserEmailInUse(String userEmail) {
        return appUserDao.getUserByLogin(userEmail).isPresent();
    }

    @Override
    public List<ValidationError> validateUser(String login, String email) {
        List<ValidationError> errors = new ArrayList<>();
        if (isUserEmailInUse(email)) {
            errors.add(new ValidationError(EMAIL_ERROR_HEADER, EMAIL_ERROR_MESSAGE));
        }
        if (isUserLoginInUse(login)) {
            errors.add(new ValidationError(LOGIN_ERROR_HEADER, LOGIN_IN_USE_ERROR_MESSAGE));
        }
        return errors;
    }

    @Override
    public boolean isLoginAndPasswordValid(String login, String hashPassword) {

        Optional<AppUser> userByLogin = appUserDao.getUserByLogin(login);
        if (userByLogin.isEmpty()) {
            return false;
        }

        String passFromDB = userByLogin.get().getPassword();
        String inputHashedPassword = DigestUtils.md5Hex(hashPassword);
        return passFromDB.equals(inputHashedPassword);
    }

    @Override
    public void registerUser(AppUser user) {
        appUserDao.saveUser(user);
    }
}
