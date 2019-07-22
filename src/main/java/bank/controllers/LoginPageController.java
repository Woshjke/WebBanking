package bank.controllers;

import bank.ApplicationProperties;
import bank.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

import static bank.ApplicationProperties.LOGIN_PAGE;
import static bank.ApplicationProperties.USER_PAGE;

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
    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showLoginPage(HttpServletRequest request) {
        ModelAndView mnv = new ModelAndView(LOGIN_PAGE);
        String isError = request.getParameter("error");
        if ("true".equals(isError)) {
            mnv.addObject("errorMessage", "Wrong credentials");
        }
        return mnv;
    }

    /**
     * Redirecting empty url to user page
     * @return user page view
     */
    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView redirectToUserPage() {
        return new ModelAndView(new RedirectView("user/" + USER_PAGE));
    }
}
