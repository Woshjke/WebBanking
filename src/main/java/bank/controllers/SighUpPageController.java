package bank.controllers;

import bank.database.UserService;
import bank.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SighUpPageController {

    private UserService service;

    @Autowired
    public SighUpPageController(UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") User user) {
        service.addUser(user);
        return "loginPage";
    }
}
