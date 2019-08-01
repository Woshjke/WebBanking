package bank;

import bank.model.entity.BankAccount;
import bank.model.entity.User;
import bank.services.dbServices.BankAccountDaoService;
import bank.services.dbServices.OrganisationDaoService;
import bank.services.dbServices.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class RequestValidator {

    private BankAccountDaoService bankAccountDaoService;
    private OrganisationDaoService organisationDaoService;
    private AuthenticationHelper authenticationHelper;
    private UserDaoService userDaoService;

    @Autowired
    public RequestValidator(BankAccountDaoService bankAccountDaoService,
                            OrganisationDaoService organisationDaoService,
                            AuthenticationHelper authenticationHelper,
                            UserDaoService userDaoService) {
        this.bankAccountDaoService = bankAccountDaoService;
        this.organisationDaoService = organisationDaoService;
        this.authenticationHelper = authenticationHelper;
        this.userDaoService = userDaoService;
    }

    public void isValidPayment(HttpServletRequest request) throws Exception {
        Long targetOrganisationId;
        Integer moneyToAdd;
        Long sourceBankAccountId;

        try {
            targetOrganisationId = Long.parseLong(request.getParameter("organisation"));
            moneyToAdd = Integer.parseInt(request.getParameter("money_count"));
            sourceBankAccountId = Long.parseLong(request.getParameter("bankAccounts"));
        } catch (NumberFormatException ex) {
            throw new Exception("Payment failed! Bad request parameters!");
        }

        if (moneyToAdd <= 0) {
            throw new Exception("Payment failed! Bad request parameters");
        }

        BankAccount sourceBankAccount = bankAccountDaoService.getBankAccountById(sourceBankAccountId);
        if (sourceBankAccount == null) {
            throw new Exception("Payment failed! Cannot find source bank account with id: " + sourceBankAccountId);
        }

        if (sourceBankAccount.getMoney() < moneyToAdd) {
            throw new Exception("Payment failed! Not enough money!");
        }

        List<BankAccount> authUserBankAccounts = authenticationHelper.getAuthenticatedUser().getBankAccounts();
        if (!authUserBankAccounts.contains(sourceBankAccount)) {
            throw new Exception("Payment failed! Authenticated user dont have bank account with id: " + sourceBankAccountId);
        }

        if (organisationDaoService.getOrganisationsById(targetOrganisationId) == null) {
            throw new Exception("Payment failed! Cannot find organisation with id: " + targetOrganisationId);
        }
    }

    public void isValidUserCreateRequest(HttpServletRequest request) throws Exception {
        String username;
        String password;

        try {
            username = request.getParameter("username");
            password = request.getParameter("password");
        } catch (NumberFormatException ex) {
            throw new Exception("User registration failed! Bad request parameters!");
        }

        if (username == null || password == null) {
            throw new Exception("User registration failed! Bad request parameters!");
        }

        if (username.equals("") || password.equals("")) {
            throw new Exception("User registration failed! Bad request parameters!");
        }

        List<User> users = userDaoService.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                throw new Exception("User registration failed! User with this username already exists!");
            }
        }

        // TODO: 01.08.2019 Провалидировать пароль
    }

    public void isValidUserUpdateRequest(HttpServletRequest request) throws Exception {
        Long userToUpdateId;
        String newUsername;
        String newPassword;

        try {
            userToUpdateId = Long.parseLong(request.getParameter("id"));
            newUsername = request.getParameter("username");
            newPassword = request.getParameter("password");
        } catch (NumberFormatException ex) {
           throw new Exception("User updating failed! Bad request parameters!");
        }

        if (newUsername == null || newPassword == null) {
            throw new Exception("User updating failed! Bad request parameters!");
        }

        if (userToUpdateId <= 0 || userToUpdateId > userDaoService.getUsers().size()) {
            throw new Exception("User updating failed! Cannot find user with id" + userToUpdateId);
        }
    }

    public void isValidUserDeleteRequest(HttpServletRequest request) throws Exception {
        Long userId;

        try {
            userId = Long.parseLong(request.getParameter("users"));
        } catch (NumberFormatException ex) {
            throw new Exception("User deleting failed! Bad request parameters!");
        }

        if (userId <= 0 || userId > userDaoService.getUsers().size()) {
            throw new Exception("User deleting failed! Cannot find user with id:" + userId);
        }
    }
}
