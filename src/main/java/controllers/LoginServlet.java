package controllers;

import dao.impl.MySQLTweetDao;
import dao.impl.MySQLUserDao;
import errors.ValidationError;
import org.apache.commons.codec.digest.DigestUtils;
import services.TweetAppService;
import services.impl.TweetAppServiceImpl;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login", ""})
public class LoginServlet extends HttpServlet {

    private TweetAppService service;

    @Override
    public void init() throws ServletException {
        service = new TweetAppServiceImpl(new MySQLUserDao(), new MySQLTweetDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = null;
        String password = null;

        // checking if cookies are delivered with request
        if (req.getCookies() != null) {
            for (Cookie c : req.getCookies()) {
                if (c.getName().equals(ServletUtils.USER_LOGIN)) {
                    login = c.getValue();
                }
                if (c.getName().equals(ServletUtils.USER_PASSWORD)) {
                    password = c.getValue();
                }
            }
        }

        // if login and password are no longer nulls it means that they were loaded form cookies in for loop above.
        if (login != null && password != null) {
            req.setAttribute(ServletUtils.USER_LOGIN, login);
            req.setAttribute(ServletUtils.USER_PASSWORD, password);
            doPost(req, resp);
            return;
        }

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hashedPassword;
        String login = req.getParameter(ServletUtils.USER_LOGIN);
        String password = req.getParameter(ServletUtils.USER_PASSWORD);

        if (login == null && password == null) {
            login = (String) req.getAttribute(ServletUtils.USER_LOGIN);
            hashedPassword = (String) req.getAttribute(ServletUtils.USER_PASSWORD);
        } else {
            hashedPassword = DigestUtils.md5Hex(password);
        }

        boolean credsInvalid = !service.isLoginAndPasswordValid(login, hashedPassword);
        if (credsInvalid) {
            ValidationError validationError = new ValidationError(ServletUtils.LOGIN_ERROR_HEADER, ServletUtils.WRONG_PASSWORD_ERROR_MESSAGE);
            ArrayList<ValidationError> errors = new ArrayList<>();
            errors.add(validationError);
            req.setAttribute(ServletUtils.ERRORS_ATTRIBUTE_NAME, errors);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        String remember = req.getParameter(ServletUtils.REMEMBER);
        if (isCheckboxChecked(remember)) {
            addCookies(resp, login, hashedPassword);
        }
        req.getSession().setAttribute(ServletUtils.USER_LOGIN, login);
        req.getRequestDispatcher("users").forward(req, resp);
    }

    private boolean isCheckboxChecked(String remember) {
        return ServletUtils.CHECKBOX_CHECKED.equals(remember);
    }

    private void addCookies(HttpServletResponse resp, String login, String hashedPassword) {
        Cookie loginCookie = new Cookie(ServletUtils.USER_LOGIN, login);
        loginCookie.setMaxAge(60 * 60);
        Cookie passCookie = new Cookie(ServletUtils.USER_PASSWORD, hashedPassword);
        passCookie.setMaxAge(60 * 60);
        resp.addCookie(loginCookie);
        resp.addCookie(passCookie);
    }
}
