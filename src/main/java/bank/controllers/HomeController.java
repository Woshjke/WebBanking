package bank.controllers;

import bank.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static bank.ApplicationProperties.HOME_PAGE;

@RestController
public class HomeController {

    private UserAccountService userAccountService;

    @Autowired
    public HomeController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @RequestMapping(value = "/home_page", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showHomepage() {
        ModelAndView mnv = new ModelAndView(HOME_PAGE);
        List<String> roles = userAccountService.getAuthUserRoles();
        mnv.addObject("userRoles", roles);
        return mnv;
    }
}
