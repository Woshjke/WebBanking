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
     * Registering user in system by calling UserDaoService method
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
        // TODO: 03.08.2019 Потом убрать это
//        user = userDaoService.getUserByUsername(username);

//        BankAccount bankAccount = new BankAccount();
//        bankAccount.setMoney(0.);
//        bankAccount.setUser(user);
//
//        bankAccountDaoService.saveBankAccount(bankAccount);
//
//        List<BankAccount> bankAccountList = new ArrayList<>();
//        bankAccountList.add(bankAccount);
//        user.setBankAccounts(bankAccountList);

//        userDaoService.updateUser(user);
//        bankAccountDaoService.updateBankAccount(bankAccount);

    }

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

    public String generateActivationCode() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random r = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i =0; i < 40; i++) {
            stringBuilder.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        return stringBuilder.toString();
    }

    public void sendEmail(String activationCode, String targetEmail) {
        String message = "To activate your account please follow this link: " +
                "http://localhost:8080/activate_account/" + activationCode;
                emailSender.sendEmail(message, "Activate your account", targetEmail);
    }
}
