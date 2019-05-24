package bank.controllers;

import bank.database.UserService;
import bank.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class LoginPageController {

    private UserService userService;

    @Autowired
    public LoginPageController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model) {//todo Узнать почему три раза заходит
        model.addAttribute("welcomingMessage", "Добро пожаловать!");
        return "loginPage";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String login(@ModelAttribute("user") User user) {
        List<User> users = userService.getUsers();
        for (User iter : users) {
            if (iter.getLogin().equals(user.getLogin()) && iter.getPassword().equals(user.getPassword())) {
                return "accountPage";
            }
        }
        return null;
    }

    @ModelAttribute("user")
    public User setSignUpForm() {
        return new User();
    }

    @RequestMapping(value = "/goToRegistrationPage")
    public String goToRegistrationPage() {
        return "registrationPage";
    }
}
