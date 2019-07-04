package bank.controllers;

import bank.database.entity.User;
import bank.services.UserService;
import bank.services.dbServices.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

import static bank.PageNameConstants.LOGIN_PAGE;

@Controller
public class LoginPageController {

    private UserDaoService userDaoService;
    private UserService userService;

    @Autowired
    public LoginPageController(UserDaoService userDaoService, UserService userService) {
        this.userDaoService = userDaoService;
        this.userService = userService;
    }


    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
    public String goToLoginPage(ModelMap model, HttpServletRequest request) {
        return LOGIN_PAGE;
    }

    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.POST)
    public String goToLoginPagePost(ModelMap model, HttpServletRequest request) {
        return LOGIN_PAGE;
    }

    @RequestMapping(value = {"/login?error"}, method = RequestMethod.GET)
    public String loginError(ModelMap model) {
        model.addAttribute("errorMessage", "Error");
        return LOGIN_PAGE;
    }

    @ModelAttribute("user")
    public User setSignUpForm() {
        return new User();
    }
}
