package bank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class LoginPageController {

    @RequestMapping(method = RequestMethod.GET)
    public String showLoginPage(ModelMap model) {
        model.addAttribute("welcomingMessage", "Добро пожаловать!");
        return "loginPage";
    }
}
