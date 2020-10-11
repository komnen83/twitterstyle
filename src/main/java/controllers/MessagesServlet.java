package controllers;

import dao.impl.MySQLTweetDao;
import dao.impl.MySQLUserDao;
import models.AppUser;
import models.Tweet;
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
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "MessagesServlet", value = "/messages")
public class MessagesServlet extends HttpServlet {

    private TweetAppService service;

    @Override
    public void init() throws ServletException {
        service = new TweetAppServiceImpl(new MySQLUserDao(), new MySQLTweetDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AppUser user = service.getUser(ServletUtils.getUserLoginFromSession(req));
        HashSet<AppUser> followedUsers = service.getFollowedUsers(user);
        List<Tweet> tweets = followedUsers
                .stream()
                .flatMap(u -> service.getUserTweets(u).stream())
                .collect(Collectors.toList());
        tweets.addAll(service.getUserTweets(user));
        req.setAttribute(ServletUtils.FOLLOWED_TWEETS, tweets);
        req.getRequestDispatcher("/messages.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
