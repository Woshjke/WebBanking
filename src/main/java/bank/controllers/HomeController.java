package bank.controllers;

import bank.AuthenticationHelper;
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

    private AuthenticationHelper authenticationHelper;
    private UserDetailsDaoService userDetailsDaoService;

    @Autowired
    public HomeController(AuthenticationHelper authenticationHelper,
                          UserDetailsDaoService userDetailsDaoService) {
        this.authenticationHelper = authenticationHelper;
        this.userDetailsDaoService = userDetailsDaoService;
    }

    @RequestMapping(value = "/home_page", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showHomepage() {
        ModelAndView mnv = new ModelAndView(HOME_PAGE);
        List<String> roles = authenticationHelper.getAuthUserRoles();
        mnv.addObject("userRoles", roles);
        UserDetails userDetails = userDetailsDaoService.findById(2L);
        String base64image = Base64.getEncoder().encodeToString(userDetails.getProfileImage());
        mnv.addObject("myImage", base64image);
        return mnv;
    }
}
