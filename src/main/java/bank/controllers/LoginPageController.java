package bank.controllers;

import bank.RequestParameter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
     * @return login page view
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showLoginPage(@ModelAttribute("error") String isError) {
        ModelAndView mnv = new ModelAndView(LOGIN_PAGE);
        if (RequestParameter.TRUE.getValue().equals(isError)) {
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
