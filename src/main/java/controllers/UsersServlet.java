package controllers;

import dao.impl.MySQLTweetDao;
import dao.impl.MySQLUserDao;
import models.AppUser;
import services.TweetAppService;
import services.impl.TweetAppServiceImpl;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

@WebServlet(name = "UsersServlet", value = "/users")
public class UsersServlet extends HttpServlet {

    TweetAppService service;
    @Override
    public void init() throws ServletException {
        service = new TweetAppServiceImpl(new MySQLUserDao(), new MySQLTweetDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userLogin = ServletUtils.getUserLoginFromSession(req);
        AppUser user = service.getUser(userLogin);
        HashSet<AppUser> followedUsers = service.getFollowedUsers(user);
        HashSet<AppUser> notFollowedUsers = service.getNotFollowedUsers(user);
        HashSet<AppUser> followers = service.getFollowers(user);

        req.setAttribute(ServletUtils.FOLLOWED_USERS, followedUsers);
        req.setAttribute(ServletUtils.NOT_FOLLOWED_USERS,  notFollowedUsers);
        req.setAttribute(ServletUtils.FOLLOWERS, followers);


    }
}
