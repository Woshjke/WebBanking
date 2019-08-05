package bank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccessDeniedController {

    @RequestMapping(value = "/403", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView accessDenied() {
        return new ModelAndView("forbidden_page");
    }

}
