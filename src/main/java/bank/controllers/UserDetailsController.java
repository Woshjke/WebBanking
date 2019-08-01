package bank.controllers;

import bank.model.entity.UserDetails;
import bank.services.dbServices.UserDaoService;
import bank.services.dbServices.UserDetailsDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Base64;

@Controller
public class UserDetailsController {

    private UserDaoService userDaoService;
    private UserDetailsDaoService userDetailsDaoService;

    @Autowired
    public UserDetailsController(UserDaoService userDaoService, UserDetailsDaoService userDetailsDaoService) {
        this.userDaoService = userDaoService;
        this.userDetailsDaoService = userDetailsDaoService;
    }

    @RequestMapping(value = "/showUserDetails", method = RequestMethod.GET)
    public ModelAndView showUserDetails() {
        ModelAndView mnv = new ModelAndView("user_details");
        UserDetails userDetails = userDetailsDaoService.findById(2L);
        String base64image = Base64.getEncoder().encodeToString(userDetails.getProfileImage());
        mnv.addObject("myImage", base64image);
        return mnv;
    }

    @RequestMapping(value = "/userDetails", method = RequestMethod.POST)
    public String doUserDetails(@RequestPart("profileImage") MultipartFile image,
                                @ModelAttribute("firstName") String firstName,
                                @ModelAttribute("lastName") String lastName,
                                @ModelAttribute("dob") String dob,
                                @ModelAttribute("phoneNumber") String phoneNumber,
                                @ModelAttribute("passId") String passId) throws IOException {
        byte[] imageBytes = image.getBytes();
        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName(firstName);
        userDetails.setLastName(lastName);
        userDetails.setDob(dob);
        userDetails.setPassId(passId);
        userDetails.setProfileImage(imageBytes);
        userDetails.setUser(userDaoService.getUserByUsername("admin"));
        userDetails.setPhoneNumber(phoneNumber);
        userDetailsDaoService.save(userDetails);
        return "admin_page";
    }
}
