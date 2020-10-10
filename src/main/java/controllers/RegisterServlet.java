package controllers;

import dao.impl.MySQLTweetDao;
import dao.impl.MySQLUserDao;
import errors.ValidationError;
import models.AppUser;
import org.apache.commons.codec.digest.DigestUtils;
import services.TweetAppService;
import services.impl.TweetAppServiceImpl;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    private TweetAppService service;

    @Override
    public void init() throws ServletException {
        service = new TweetAppServiceImpl(new MySQLUserDao(), new MySQLTweetDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(ServletUtils.USER_LOGIN);
        String email = req.getParameter(ServletUtils.USER_EMAIL);
        List<ValidationError> validationErrors = service.validateUser(login, email);
        if (!validationErrors.isEmpty()) {
            req.setAttribute(ServletUtils.ERRORS_ATTRIBUTE_NAME, validationErrors);
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        AppUser user = AppUser.UserBuilder
                .getBuilder()
                .login(login)
                .email(email)
                .lastName(req.getParameter(ServletUtils.USER_SURNAME))
                .password(DigestUtils.md5Hex(req.getParameter(ServletUtils.USER_PASSWORD)))
                .name(req.getParameter(ServletUtils.USER_NAME))
                .build();
        service.registerUser(user);

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
}
