package bank.controllers;

import bank.ApplicationProperties;
import javafx.application.Application;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static bank.ApplicationProperties.LOGIN_PAGE;

/**
 * Authentication controller
 */
@Controller
public class LoginPageController {

    /**
     *
     * @param request - request with auth error from Spring Security
     * @return login page view
     */
    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
    public ModelAndView getLoginPage(HttpServletRequest request) {
        ModelAndView mnv = new ModelAndView(LOGIN_PAGE);
        String isError = request.getParameter("error");
        if ("true".equals(isError)) {
            mnv.addObject("errorMessage", "Wrong credentials");
        }
        System.out.println(new ApplicationProperties().getProperty("database.username"));
        return mnv;
    }

    /**
     *
     * @param request - request with auth error from Spring Security
     * @return login page view
     */
    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.POST)
    public ModelAndView postLoginPage(HttpServletRequest request) {
        ModelAndView mnv = new ModelAndView(LOGIN_PAGE);
        String isError = request.getParameter("error");
        if ("true".equals(isError)) {
            mnv.addObject("errorMessage", "Wrong credentials");
        }
        return mnv;
    }
}
