package bank.services;

import bank.email.EmailSender;
import bank.model.entity.Role;
import bank.model.entity.User;
import bank.model.entity.UserDetails;
import bank.services.dbServices.RoleDaoService;
import bank.services.dbServices.UserDaoService;
import bank.services.dbServices.UserDetailsDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This service performing all things you need to register a new user.
 */
@Service
@Transactional
public class RegistrationService {

    private UserDaoService userDaoService;
    private RoleDaoService roleDaoService;
    private UserDetailsDaoService userDetailsDaoService;
    private EmailSender emailSender;

    @Autowired
    public RegistrationService(UserDaoService userDaoService,
                               RoleDaoService roleDaoService,
                               UserDetailsDaoService userDetailsDaoService,
                               EmailSender emailSender) {
        this.userDaoService = userDaoService;
        this.roleDaoService = roleDaoService;
        this.userDetailsDaoService = userDetailsDaoService;
        this.emailSender = emailSender;
    }

    /**
     * This method register user in database.
     * @param username - username of a new user.
     * @param password - password of a new user.
     * @param activationCode - activation account code for this user.
     * @return registered User object.
     */
    public User registerUser(String username, String password, String activationCode) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder(11).encode(password));
        Set<Role> userRoles = new HashSet<>();
        List<Role> roles = roleDaoService.getRoles();
        for (Role iter : roles) {
            if (iter.getName().equals("ROLE_USER")) {
                userRoles.add(iter);
            }
        }
        user.setRoles(userRoles);
        user.setActivationCode(activationCode);
        user.setStatus("disabled");
        userDaoService.createUser(user);
        return user;
    }

    /**
     * This method calling when admin want to register a new user without sending an email with activation link.
     * @param username - username of a new user.
     * @param password - password of a new user.
     * @return registered User object.
     */
    public User registerUserByAdmin(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder(11).encode(password));
        Set<Role> userRoles = new HashSet<>();
        List<Role> roles = roleDaoService.getRoles();
        for (Role iter : roles) {
            if (iter.getName().equals("ROLE_USER")) {
                userRoles.add(iter);
            }
        }
        user.setRoles(userRoles);
        user.setActivationCode("");
        user.setStatus("active");

        userDaoService.createUser(user);

        return user;
    }

    /**
     * This method saves user details in database.
     * @param firstName - first name of a user.
     * @param lastName - last name of a user.
     * @param dob - day of birdth of a user.
     * @param passId - pass ID of a user.
     * @param phoneNumber - phone number of a user.
     * @param email - email of a user.
     * @param image - profile image of a user.
     * @param user - User object that connected with this user details.
     * @throws IOException - if method cannot get bytes form image.
     */
    public void setUserDetails(String firstName,
                               String lastName,
                               String dob,
                               String passId,
                               String phoneNumber,
                               String email,
                               MultipartFile image,
                               User user) throws IOException {

        byte[] imageBytes = image.getBytes();
        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName(firstName);
        userDetails.setLastName(lastName);
        userDetails.setDob(dob);
        userDetails.setPassId(passId);
        userDetails.setProfileImage(imageBytes);
        userDetails.setUser(userDaoService.getUserByUsername("admin"));
        userDetails.setPhoneNumber(phoneNumber);
        userDetails.setEmail(email);
        userDetails.setUser(user);
        userDetailsDaoService.save(userDetails);
    }

    /**
     * Method generating activation code for a new user.
     * @return activation code string.
     */
    public String generateActivationCode() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random r = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 40; i++) {
            stringBuilder.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        return stringBuilder.toString();
    }


    /**
     * This method sending message with activation link on user email, that the user specified during registration.
     *
     * @param activationCode - code, that was generated while registration. This code - it's a part of activation link.
     * @param targetEmail    - email, that the user specified during registration. Message with activation link will be
     *                       sent to this email.
     */
    public void sendEmail(String activationCode, String targetEmail) {
        String message = "To activate your account please follow this link: " +
                "http://localhost:8080/activate_account/" + activationCode;
        emailSender.sendEmail(message, "Activate your account", targetEmail);
    }
}
