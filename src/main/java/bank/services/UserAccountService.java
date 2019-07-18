package bank.services;

import bank.model.entity.BankAccount;
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
import java.util.List;
import java.util.Scanner;

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
     * @param request - request form JSP with needed parameters
     * @return was payment completed or not
     */
    public boolean doPayment(HttpServletRequest request) {

        Long selectedOrgId;
        Integer moneyToAdd;
        Long selectedBankAccountId;

        try {
            selectedOrgId = Long.parseLong(request.getParameter("organisation"));
            moneyToAdd = Integer.parseInt(request.getParameter("money_count"));
            selectedBankAccountId = Long.parseLong(request.getParameter("bankAccounts"));
        } catch (NumberFormatException ex) {
            return false;
        }

        BankAccount sourceBankAccount = bankAccountService.getBankAccountById(selectedBankAccountId);

        List<BankAccount> authUserBankAccounts = getAuthenticatedUser().getBankAccounts();
        if (!isContainsBankAccount(authUserBankAccounts, sourceBankAccount)) {
            return false;
        }

        sourceBankAccount.takeMoney(moneyToAdd);
        Long userId = organisationService.getOrganisationsById(selectedOrgId)
                .getBankAccountList().get(0)
                .getUser().getId();

        User user = userDaoService.getUserById(userId);
        BankAccount orgBankAccount = new ArrayList<>(user.getBankAccounts()).get(0);
        orgBankAccount.addMoney(moneyToAdd);

        Transaction transaction = new Transaction(sourceBankAccount, orgBankAccount, moneyToAdd);
        transactionDaoService.createTransaction(transaction);

        bankAccountService.updateBankAccount(sourceBankAccount);
        bankAccountService.updateBankAccount(orgBankAccount);
        return true;
    }

    /**
     * This method transfers money direct from the user account to the another user account
     * @param request - request form JSP with needed parameters
     * @return was transaction completed or not
     */
    public boolean doTransaction(HttpServletRequest request) {

        BankAccount sourceBankAccount;
        BankAccount destinationBankAccount;
        Integer money_value;

        try {
            sourceBankAccount = bankAccountService.
                    getBankAccountById(Long.parseLong(request.getParameter("source")));
            destinationBankAccount = bankAccountService.
                    getBankAccountById(Long.parseLong(request.getParameter("destination")));
            money_value = Integer.parseInt(request.getParameter("value"));
        } catch (NumberFormatException ex) {
            return false;
        }

        List<BankAccount> authUserBankAccounts = getAuthenticatedUser().getBankAccounts();
        if (!isContainsBankAccount(authUserBankAccounts, sourceBankAccount)) {
            return false;
        }

        if (destinationBankAccount == null) {
            System.out.println("Cannot find bank account with id: " +
                    Long.parseLong(request.getParameter("destination")));
            return false;
        }
        if (money_value > sourceBankAccount.getMoney()) {
            System.out.println("Not enough money!");
            return false;
        }

        sourceBankAccount.takeMoney(money_value);
        destinationBankAccount.addMoney(money_value);

        bankAccountService.updateBankAccount(sourceBankAccount);
        bankAccountService.updateBankAccount(destinationBankAccount);

        Transaction transaction = new Transaction(sourceBankAccount,
                destinationBankAccount,
                money_value);
        transactionDaoService.createTransaction(transaction);
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
     * This method returns authenticated user object form database
     * @return authenticated user object form database
     */
    public User getAuthenticatedUser() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userDaoService.getUserByUsername(username);
    }

    /**
     * This methods checking if list of bank accounts contains specific bank account
     * list.contains() not working cuz different objects hash
     * @param bankAccounts - list of bank accounts
     * @param bankAccount - bank account that we checking
     * @return contains list needed bank account or not
     */
    private boolean isContainsBankAccount(List<BankAccount> bankAccounts, BankAccount bankAccount) {
        boolean isContainsBankAccount = false;
        for (BankAccount iter : bankAccounts) {
            if (iter.getId().equals(bankAccount.getId())) {
                isContainsBankAccount = true;
            }
        }
        return isContainsBankAccount;
    }

}
