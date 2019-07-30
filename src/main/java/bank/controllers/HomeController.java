package bank.controllers;

import bank.AuthenticationHelper;
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

    private AuthenticationHelper authenticationHelper;

    @Autowired
    public HomeController(AuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }

    @RequestMapping(value = "/home_page", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showHomepage() {
        ModelAndView mnv = new ModelAndView(HOME_PAGE);
        List<String> roles = authenticationHelper.getAuthUserRoles();
        mnv.addObject("userRoles", roles);
        return mnv;
    }
}
