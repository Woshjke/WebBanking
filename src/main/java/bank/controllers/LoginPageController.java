package bank.controllers;

import bank.model.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static bank.PageNameConstants.LOGIN_PAGE;

// TODO: 15.07.2019 Поискать на .equals и развернуть
@Controller
public class LoginPageController {

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView goToLoginPage(HttpServletRequest request) {
        ModelAndView mnv = new ModelAndView(LOGIN_PAGE);
        String isError = request.getParameter("error");
        if ("true".equals(isError)) {
            mnv.addObject("errorMessage", "Wrong credentials");
        }

        return mnv;
    }

    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.POST)
    public String goToLoginPagePost() {
        return LOGIN_PAGE;
    }


    @ModelAttribute("user")
    public User setSignUpForm() {
        return new User();
    }
}
