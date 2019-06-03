package bank.controllers;

import bank.database.entity.User;
import bank.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminAccountController {

    private UserService userService;

    @Autowired
    public AdminAccountController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public String createUser() {
        return "createUserPage";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute(value = "user") User user) {
        userService.createUser(user);
        return "adminPage";
    }

    @ModelAttribute("user")
    public User setSignUpForm() {
        return new User();
    }
}
