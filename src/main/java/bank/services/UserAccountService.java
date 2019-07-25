package bank.services;

import bank.model.entity.BankAccount;
import bank.model.entity.Role;
import bank.model.entity.Transaction;
import bank.model.entity.User;
import bank.services.dbServices.BankAccountDaoService;
import bank.services.dbServices.OrganisationDaoService;
import bank.services.dbServices.TransactionDaoService;
import bank.services.dbServices.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAccountService {
    private OrganisationDaoService organisationService;
    private UserDaoService userDaoService;
    private BankAccountDaoService bankAccountService;
    private TransactionDaoService transactionDaoService;

    public static final String NBRB_RATES_URL = "http://www.nbrb.by/API/ExRates/Rates/";

    @Autowired
    public UserAccountService(OrganisationDaoService organisationService,
                              UserDaoService userDaoService,
                              BankAccountDaoService bankAccountService,
                              TransactionDaoService transactionDaoService) {
        this.organisationService = organisationService;
        this.userDaoService = userDaoService;
        this.bankAccountService = bankAccountService;
        this.transactionDaoService = transactionDaoService;
    }

    /**
     * This method transfers money from the user account to the organization account
     *
     * @param request - request form JSP with needed parameters
     * @return was payment completed or not
     */
    public boolean doPayment(HttpServletRequest request) {
        Long targetOrganisationId; //ID организации, для которой пользователя отправляет деньги
        Integer moneyToAdd; //Сколько нужно отправить денег организации
        Long sourceBankAccountId; //Из какого банковского счета пользователя нужно списывать деньги

        //пробуем получить значения переменных из request-а
        try {
            targetOrganisationId = Long.parseLong(request.getParameter("organisation"));
            moneyToAdd = Integer.parseInt(request.getParameter("money_count"));
            sourceBankAccountId = Long.parseLong(request.getParameter("bankAccounts"));
        } catch (NumberFormatException ex) {
            return false;
        }
        //todo проверить на минус
        //хватает ли у пользователя денег на счету
        BankAccount sourceBankAccount = bankAccountService.getBankAccountById(sourceBankAccountId);
        if (sourceBankAccount.getMoney() < moneyToAdd) {
            return false;
        }

        //проверка на то, пренадлежит ли найденный аккаунт залогиненному пользователю (тот что производит операцию)
        List<BankAccount> authUserBankAccounts = getAuthenticatedUser().getBankAccounts();
        if (!authUserBankAccounts.contains(sourceBankAccount)) {
            return false;
        }

        //забираем деньги у пользователя
        sourceBankAccount.takeMoney(moneyToAdd);

        //отдаем организации
        //todo поменить аккаунт для входящих денег флагом. useForDefaultIncomingPayment
        BankAccount orgBankAccount = organisationService.getOrganisationsById(targetOrganisationId)
                .getBankAccountList().get(0);
        orgBankAccount.addMoney(moneyToAdd);

        //создаем запись операции в базе
        Transaction transaction = new Transaction(sourceBankAccount, orgBankAccount, moneyToAdd);
        transactionDaoService.createTransaction(transaction);

        //сохраняем изменения в базе
        bankAccountService.updateBankAccount(sourceBankAccount);
        bankAccountService.updateBankAccount(orgBankAccount);
        return true;
    }

    /**
     * This method transfers money direct from the user account to the another user account
     *
     * @param request - request form JSP with needed parameters
     * @return was transaction completed or not
     */
    public boolean doTransaction(HttpServletRequest request) {
        BankAccount sourceBankAccount;
        BankAccount destinationBankAccount;
        Integer moneyValue;

        try {
            sourceBankAccount = bankAccountService.
                    getBankAccountById(Long.parseLong(request.getParameter("source")));
            destinationBankAccount = bankAccountService.
                    getBankAccountById(Long.parseLong(request.getParameter("destination")));
            moneyValue = Integer.parseInt(request.getParameter("value"));
        } catch (NumberFormatException ex) {
            return false;
        }

        List<BankAccount> authUserBankAccounts = getAuthenticatedUser().getBankAccounts();
        if (!authUserBankAccounts.contains(sourceBankAccount)) {
            return false;
        }

        if (destinationBankAccount == null) {
            System.out.println("Cannot find bank account with id: " +
                    Long.parseLong(request.getParameter("destination")));
            return false;
        }
        if (moneyValue > sourceBankAccount.getMoney()) {
            System.out.println("Not enough money!");
            return false;
        }

        sourceBankAccount.takeMoney(moneyValue);
        destinationBankAccount.addMoney(moneyValue);

        bankAccountService.updateBankAccount(sourceBankAccount);
        bankAccountService.updateBankAccount(destinationBankAccount);

        Transaction transaction = new Transaction(sourceBankAccount,
                destinationBankAccount,
                moneyValue);
        transactionDaoService.createTransaction(transaction);
        return true;
    }

    public boolean addOrganisation(HttpServletRequest request) {
        // TODO: 23.07.2019 Проверить пренадлежит ли аккаунт залогиненому пользователю
        Long selectedBankAccountID;
        try {
            selectedBankAccountID = Long.valueOf(request.getParameter("bankAccount"));
        } catch (NumberFormatException ex) {
            return false;
        }
        BankAccount bankAccount = bankAccountService.getBankAccountById(selectedBankAccountID);

        return true;
    }

    /**
     * This method getting currency rate form National Bank of the Republic of Belarus in JSON,
     * converting it to object and returning this object
     *
     * @param currency - needed currency to get form NBRB
     * @return JSON string
     */

    public String getCurrencyRate(String currency) {
        String url = NBRB_RATES_URL + currency + "?ParamMode=2";
        URL urlObj;
        String json = "";
        try {
            urlObj = new URL(null, url, new sun.net.www.protocol.https.Handler());
            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");
            InputStream response = connection.getInputStream();
            json = new Scanner(response, "UTF-8").nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * This method returns authenticated user object
     *
     * @return authenticated user object form database
     */
    public User getAuthenticatedUser() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authUser;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        if (username.equals("anonymousUser")) {
            authUser = new User();
            Set<Role> roles = new HashSet<>();
            roles.add(new Role("ROLE_ANONYMOUS"));
            authUser.setUsername("anonymous");
            authUser.setRoles(roles);
        } else {
            authUser = userDaoService.getUserByUsername(username);
        }
        return authUser;
    }

    public List<String> getAuthUserRoles() {
        return getAuthenticatedUser().getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
