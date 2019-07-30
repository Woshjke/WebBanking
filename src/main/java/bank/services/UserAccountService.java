package bank.services;

import bank.AuthenticationHelper;
import bank.RequestValidator;
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
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAccountService {
    private OrganisationDaoService organisationService;
    private BankAccountDaoService bankAccountService;
    private TransactionDaoService transactionDaoService;
    private AuthenticationHelper authenticationHelper;

    private static final String NBRB_RATES_URL = "http://www.nbrb.by/API/ExRates/Rates/";

    @Autowired
    public UserAccountService(OrganisationDaoService organisationService,
                              BankAccountDaoService bankAccountService,
                              TransactionDaoService transactionDaoService,
                              AuthenticationHelper authenticationHelper) {
        this.organisationService = organisationService;
        this.bankAccountService = bankAccountService;
        this.transactionDaoService = transactionDaoService;
        this.authenticationHelper = authenticationHelper;
    }

    /**
     * This method transfers money from the user account to the organization account
     *
     * @param request - request form JSP with needed parameters
     */
    public void doPayment(HttpServletRequest request) {
            Long targetOrganisationId = Long.parseLong(request.getParameter("organisation"));
            Integer moneyToAdd = Integer.parseInt(request.getParameter("money_count"));
            Long sourceBankAccountId = Long.parseLong(request.getParameter("bankAccounts"));

            //забираем деньги у пользователя
            BankAccount sourceBankAccount = bankAccountService.getBankAccountById(sourceBankAccountId);
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

        List<BankAccount> authUserBankAccounts = authenticationHelper.getAuthenticatedUser().getBankAccounts();
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
}
