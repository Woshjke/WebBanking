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

    public void isValidAddMoneyRequest(HttpServletRequest request) throws Exception {
        Long bankAccountID;
        Integer moneyToAdd;
        try {
            bankAccountID = Long.valueOf(request.getParameter("bankAccounts"));
            moneyToAdd = Integer.valueOf(request.getParameter("moneyToAdd"));
        } catch (NumberFormatException ex) {
            throw new Exception("Money adding error! Bad request params!");
        }

        if (moneyToAdd < 0) {
            throw new Exception("Money adding error! Money value need to be > 0");
        }

        if (bankAccountDaoService.getBankAccountById(bankAccountID) == null) {
            throw new Exception("Money adding error! Cannot find bank account with id: " + bankAccountID);
        }
    }

    public void isValidTransactionRequest(HttpServletRequest request) throws Exception {

        Long sourceBankAccountId;
        Long destinationBankAccountCardNumber;
        Integer moneyValue;

        try {
            sourceBankAccountId = Long.valueOf(request.getParameter("source"));
            destinationBankAccountCardNumber = Long.valueOf(request.getParameter("destination"));
            moneyValue = Integer.valueOf(request.getParameter("value"));
        } catch (NumberFormatException ex) {
            throw new Exception("Transaction failed! Wrong data format!");
        }

        if (sourceBankAccountId < 0) {
            throw new Exception("Wrong input! Source account id should be positive!");
        }

        if (destinationBankAccountCardNumber < 0) {
            throw new Exception("Wrong input! Card number should be positive!");
        }

        if (moneyValue < 0) {
            throw new Exception("Wrong input! Money value should be positive!");
        }

        BankAccount sourceBankAccount = bankAccountDaoService.
                getBankAccountById(sourceBankAccountId);
        BankAccount destinationBankAccount = bankAccountDaoService.
                getBankAccountByCardNumber(request.getParameter("destination"));

        if (sourceBankAccount == null || destinationBankAccount == null) {
            throw new Exception("Wrong input!");
        }

        if (destinationBankAccount.equals(sourceBankAccount)){
            throw new Exception("Wrong input! You can't send the money to the same bank account you are using");
        }

        List<BankAccount> authUserBankAccounts = authenticationHelper.getAuthenticatedUser().getBankAccounts();
        if (!authUserBankAccounts.contains(sourceBankAccount)) {
            throw new Exception("Transaction failed! Authenticated user doesn't have bank account with id: " + sourceBankAccountId);
        }

        if (sourceBankAccount.getMoney() < moneyValue) {
            throw new Exception("Transaction failed! Not enough money!");
        }

    }

    public void isValidActivateRequest(HttpServletRequest request) throws Exception {
        Long userId;
        try {
            userId = Long.valueOf(request.getParameter("users"));
        } catch (NumberFormatException ex) {
            throw new Exception("Activation failed! Bad request parameters!");
        }

        if (userId < 0 || userDaoService.getUserById(userId) == null) {
            throw new Exception("Activation failed! Bad request parameters!");
        }
    }
}
