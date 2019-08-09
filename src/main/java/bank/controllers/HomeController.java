package bank.controllers;

import bank.AuthenticationHelperService;
import bank.model.entity.UserDetails;
import bank.services.dbServices.UserDetailsDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Base64;
import java.util.List;

import static bank.ApplicationProperties.HOME_PAGE;

@RestController
public class HomeController {

    private AuthenticationHelperService authenticationHelperService;
    private UserDetailsDaoService userDetailsDaoService;

    @Autowired
    public HomeController(AuthenticationHelperService authenticationHelperService,
                          UserDetailsDaoService userDetailsDaoService) {
        this.authenticationHelperService = authenticationHelperService;
        this.userDetailsDaoService = userDetailsDaoService;
    }

    @RequestMapping(value = "/home_page", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showHomepage() {
        ModelAndView mnv = new ModelAndView(HOME_PAGE);
        List<String> roles = authenticationHelperService.getAuthUserRoles();
        mnv.addObject("userRoles", roles);
        UserDetails userDetails = userDetailsDaoService.findById(2L);
        String base64image = Base64.getEncoder().encodeToString(userDetails.getProfileImage());
        mnv.addObject("myImage", base64image);
        return mnv;
    }
}
