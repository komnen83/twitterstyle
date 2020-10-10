package utils;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {
    public static final String ERRORS_ATTRIBUTE_NAME = "errors";
    public static final String USER_NAME = "name";
    public static final String USER_SURNAME = "surname";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_EMAIL = "email";
    public static final String REMEMBER = "remember";
    public static final String CHECKBOX_CHECKED = "on";
    public static final String LOGIN_ERROR_HEADER = "INVALID LOGIN";
    public static final String EMAIL_ERROR_HEADER = "INVALID EMAIL";
    public static final String PASSWORD_ERROR_HEADER = "INVALID PASSWORD";
    public static final String LOGIN_NOT_EXIST_MESSAGE = "Typed login do not exist";
    public static final String LOGIN_IN_USE_ERROR_MESSAGE = "Typed login is already in use";
    public static final String WRONG_PASSWORD_ERROR_MESSAGE = "Typed password do not match user";
    public static final String EMAIL_ERROR_MESSAGE = "Typed email is already in use";
    public static final String FOLLOWED_USERS = "followedUsers";
    public static final String NOT_FOLLOWED_USERS = "notFollowedUsers";
    public static final String USER_LOGIN_TO_FOLLOW = "userLoginToFollow";
    public static final String USER_LOGIN_TO_STOP_FOLLOW = "userLoginToUnfollow";
    public static final String FOLLOWED_TWEETS = "tweets";
    public static final String TWEET_MESSAGE_PARAM = "tweetMessage";
    public static String getUserLoginFromSession(HttpServletRequest req) {
        return (String) req.getSession().getAttribute(USER_LOGIN);
    }
}
