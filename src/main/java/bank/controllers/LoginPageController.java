package bank.controllers;

import bank.model.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import static bank.PageNameConstants.LOGIN_PAGE;

@Controller
public class LoginPageController {

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String goToLoginPage() {
        return LOGIN_PAGE;
    }

    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.POST)
    public String goToLoginPagePost() {
        return LOGIN_PAGE;
    }

    @RequestMapping(value = {"/login?error=true"}, method = RequestMethod.GET)
    public ModelAndView loginError() {
        ModelAndView mnv = new ModelAndView(LOGIN_PAGE);
        mnv.addObject("errorMessage", "Error");
        return mnv;
    }

    @ModelAttribute("user")
    public User setSignUpForm() {
        return new User();
    }
}
