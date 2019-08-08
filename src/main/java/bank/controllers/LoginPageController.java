package bank.controllers;

import bank.RequestParameter;
import bank.RequestValidator;
import bank.email.EmailSender;
import bank.model.entity.User;
import bank.model.entity.UserDetails;
import bank.services.RegistrationService;
import bank.services.dbServices.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

import static bank.ApplicationProperties.*;

/**
 * Authentication controller
 */
@Controller
public class LoginPageController {

    private RequestValidator validator;
    private RegistrationService registrationService;
    private UserDaoService userDaoService;

    @Autowired
    public LoginPageController(RequestValidator validator,
                               RegistrationService registrationService,
                               UserDaoService userDaoService) {
        this.validator = validator;
        this.registrationService = registrationService;
        this.userDaoService = userDaoService;
    }

    /**
     *
     * @return login page view
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showLoginPage(@ModelAttribute("error") String isError) {
        ModelAndView mnv = new ModelAndView(LOGIN_PAGE);
        if (RequestParameter.TRUE.getValue().equals(isError)) {
            mnv.addObject("errorMessage", "Wrong credentials");
        }
        return mnv;
    }

    /**
     * Handling request of getting user registration page
     *
     * @return user registration view
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showCreateUserPage() {
        return new ModelAndView(CREATE_USER_PAGE);
    }

    /**
     * handling user registration request and calls registration service
     *
     * @param request - request from JSP
     * @return admin page view
     */
    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    public ModelAndView doCreateUser(HttpServletRequest request,
                                     @RequestPart("profileImage") MultipartFile image) {
        ModelAndView mnv = new ModelAndView(LOGIN_PAGE);
        try {
            validator.isValidUserCreateRequest(request);
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String activationCode = registrationService.generateActivationCode();
            User user = registrationService.registerUser(username, password, activationCode);
            //todo Тут будет валидатор
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String dob = request.getParameter("dob");
            String phoneNumber = request.getParameter("phoneNumber");
            String passId = request.getParameter("passId");
            String email = request.getParameter("email");
            registrationService.setUserDetails(
                    firstName,
                    lastName,
                    dob,
                    passId,
                    phoneNumber,
                    email,
                    image,
                    user);
            registrationService.sendEmail(user.getActivationCode(), email);
            return mnv;
        } catch (Exception ex) {
            return mnv;
        }
    }

    @RequestMapping(value = "/after_activation_page", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showAfterActivationPage() {
        return new ModelAndView(AFTER_ACTIVATION_PAGE);
    }

    @RequestMapping(value = "/activate_account/{activationCode}")
    public ModelAndView activateUser(@PathVariable String activationCode) {
        User user = userDaoService.getUserByActivationCode(activationCode);
        user.setStatus("active");
        user.setActivationCode("");
        userDaoService.updateUser(user);
        return new ModelAndView(AFTER_ACTIVATION_PAGE);
    }

    /**
     * Redirecting empty url to user page
     * @return user page view
     */
    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView redirectToUserPage() {
        return new ModelAndView(new RedirectView("user/" + USER_PAGE));
    }
}
