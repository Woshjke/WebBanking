package bank.controllers;

import bank.database.UserHelper;
import bank.database.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class LoginPageController {

    @RequestMapping(method = RequestMethod.GET)
    public String showLoginPage(ModelMap model) {//todo Узнать почему три раза заходит
        model.addAttribute("welcomingMessage", "Добро пожаловать!");
        return "loginPage";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String login(@ModelAttribute("user") User user, BindingResult result, ModelMap model) {
        new UserHelper().addUser(new User("admin", "admin"));
        return "accountPage";
    }

    @ModelAttribute("user")
    public User setSignUpForm() {
        return new User();
    }
}
