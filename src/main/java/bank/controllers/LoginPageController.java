package bank.controllers;

import bank.database.entity.User;
import bank.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    public String goToLoginPage(ModelMap model) {
        model.addAttribute("welcomingMessage", "Добро пожаловать!");
        return "loginPage";
    }

    //todo Сделать нормальые проверки + хешировать пароль
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String login(@ModelAttribute("user") User user, ModelMap map) {
        List<User> users = userService.getUsers();
        for (User iter : users) {
            if (iter.getLogin().equals(user.getLogin()) && iter.getPassword().equals(user.getPassword())) {
                map.addAttribute("currentUser", user);
                if (iter.isAdmin()) {
                    return "adminPage";
                } else return "userPage";
            }
        }
        return null;
    }

    @ModelAttribute("user")
    public User setSignUpForm() {
        return new User();
    }
}
