package bank.controllers;

import bank.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.soap.SOAPBinding;

@Controller
@RequestMapping("/")
public class LoginPageController {

    @RequestMapping(method = RequestMethod.GET)
    public String showLoginPage(ModelMap model) {//todo Узнать почему три раза заходит
        model.addAttribute("welcomingMessage", "Добро пожаловать!");
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String login(@ModelAttribute("user") User user, BindingResult result) {
        //model.addAttribute("welcomingMessage", "Добро пожаловать!");
        //System.out.println(model);
        return "moneyTransfer";
    }

    @ModelAttribute("user")
    public User setSignUpForm() {
        return new User();
    }
}
