package bank;

import bank.model.entity.BankAccount;
import bank.model.entity.Organisations;
import bank.model.entity.Role;
import bank.model.entity.User;
import bank.services.dbServices.BankAccountDaoService;
import bank.services.dbServices.OrganisationDaoService;
import bank.services.dbServices.RoleDaoService;
import bank.services.dbServices.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * This class performing back-end validation of requests.
 */
@Service
public class RequestValidator {

    private BankAccountDaoService bankAccountDaoService;
    private OrganisationDaoService organisationDaoService;
    private AuthenticationHelperService authenticationHelperService;
    private UserDaoService userDaoService;
    private RoleDaoService roleDaoService;

    @Autowired
    public RequestValidator(BankAccountDaoService bankAccountDaoService,
                            OrganisationDaoService organisationDaoService,
                            AuthenticationHelperService authenticationHelperService,
                            UserDaoService userDaoService,
                            RoleDaoService roleDaoService) {
        this.bankAccountDaoService = bankAccountDaoService;
        this.organisationDaoService = organisationDaoService;
        this.authenticationHelperService = authenticationHelperService;
        this.userDaoService = userDaoService;
        this.roleDaoService = roleDaoService;
    }

    /**
     * Method validating payment request.
     * @param request - payment request.
     * @throws Exception - exception with specific validating error message.
     */
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

        List<BankAccount> authUserBankAccounts =
                authenticationHelperService.getAuthenticatedUser().getBankAccounts();
        if (!authUserBankAccounts.contains(sourceBankAccount)) {
            throw new Exception("Payment failed! Authenticated user dont have bank account with id: "
                    + sourceBankAccountId);
        }

        if (organisationDaoService.getOrganisationsById(targetOrganisationId) == null) {
            throw new Exception("Payment failed! Cannot find organisation with id: " + targetOrganisationId);
        }
    }

    /**
     * Method validating user creating request.
     * @param request -user creating request.
     * @throws Exception - exception with specific validating error message.
     */
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

        if (!password.matches("(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*")) {
            throw new Exception("Weak password!");
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

    }

    /**
     * Method validating user updating request.
     * @param request -user updating request.
     * @throws Exception - exception with specific validating error message.
     */
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

    /**
     * Method validating user delete request.
     * @param request -user delete request.
     * @throws Exception - exception with specific validating error message.
     */
    public void isValidUserDeleteRequest(HttpServletRequest request) throws Exception {
        Long userId;
        User authUser = authenticationHelperService.getAuthenticatedUser(false);
        try {
            userId = Long.parseLong(request.getParameter("users"));
        } catch (NumberFormatException ex) {
            throw new Exception("User deleting failed! Bad request parameters!");
        }

        if (userId <= 0 || userId > userDaoService.getUsers().size()) {
            throw new Exception("User deleting failed! Cannot find user with id:" + userId);
        }

        if (userId.equals(authUser.getId())) {
            throw new Exception("You cannot delete your account");
        }
    }

    /**
     * Method validating money adding request.
     * @param request - money adding request.
     * @throws Exception - exception with specific validating error message.
     */
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

    /**
     * Method validating card-to-card transaction request.
     * @param request - card-to-card transaction request.
     * @throws Exception - exception with specific validating error message.
     */
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

        List<BankAccount> authUserBankAccounts = authenticationHelperService.getAuthenticatedUser().getBankAccounts();
        if (!authUserBankAccounts.contains(sourceBankAccount)) {
            throw new Exception("Transaction failed! Authenticated user doesn't have bank account with id: "
                    + sourceBankAccountId);
        }

        if (sourceBankAccount.getMoney() < moneyValue) {
            throw new Exception("Transaction failed! Not enough money!");
        }

    }

    /**
     * Method validating user details creating request.
     * @param request - user details creating request.
     * @throws Exception - exception with specific validating error message.
     */
    public void isValidUserDetailsCreateRequest(HttpServletRequest request) throws Exception {
        String firstName;
        String lastName;
        String dob;
        String phoneNumber;
        String passId;
        String email;

        try {
            firstName = request.getParameter("firstName");
            lastName = request.getParameter("lastName");
            dob = request.getParameter("dob");
            phoneNumber = request.getParameter("phoneNumber");
            passId = request.getParameter("passId");
            email = request.getParameter("email");
        } catch (NumberFormatException ex) {
            throw new Exception("Wrong user details!");
        }

        if (firstName == null || lastName == null || dob == null ||
                phoneNumber == null || passId == null || email == null) {
            throw new Exception("Wrong user details!");
        }
    }

    /**
     * Method validating setting role request.
     * @param request - setting role request.
     * @throws Exception - exception with specific validating error message.
     */
    public void isValidSetRoleRequest(HttpServletRequest request) throws Exception {
        Long userId;
        String roleName;

        try {
            userId = Long.valueOf(request.getParameter("users"));
            roleName = request.getParameter("role");
        } catch (NumberFormatException ex) {
            throw new Exception("Error! Bad request parameters");
        }

        User user = userDaoService.getUserByIdWithFetchRoles(userId);
        Role role = roleDaoService.getRoleByName(roleName);

        if (user == null) {
            throw new Exception("Cannot find user!");
        }

        if (role == null) {
            throw new Exception("Cannot find role!");
        }

        if (user.getRoles().contains(role)) {
            throw new Exception("User already have this role: " + role.getName());
        }
    }

    /**
     * Method validating adding organisation request.
     * @param request - adding organisation request.
     * @throws Exception - exception with specific validating error message.
     */
    public void isValidAddOrganisationRequest(HttpServletRequest request) throws Exception {
        Long bankAccountId;
        String organisationName;
        try {
            bankAccountId = Long.valueOf(request.getParameter("bankAccounts"));
            organisationName = request.getParameter("organisationName");
        } catch (NumberFormatException ex) {
            throw new Exception("Invalid request data!");
        }

        List<Organisations> allOrganisations = organisationDaoService.getOrganisations();
        for (Organisations iter: allOrganisations) {
            if (iter.getName().equals(organisationName)) {
                throw new Exception("Organisation with this name already exists!");
            }
        }

        BankAccount bankAccount = bankAccountDaoService.getBankAccountById(bankAccountId);

        if (bankAccount == null) {
            throw new Exception("Invalid request data!");
        }

        if (organisationName.isEmpty()) {
            throw new Exception("Invalid request data!");
        }

    }
}
