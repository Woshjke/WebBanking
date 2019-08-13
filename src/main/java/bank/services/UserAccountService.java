package bank.services;

import bank.AuthenticationHelperService;
import bank.BankCardNumberGenerator;
import bank.RequestValidator;
import bank.model.entity.BankAccount;
import bank.model.entity.Organisations;
import bank.model.entity.Transaction;
import bank.services.dbServices.BankAccountDaoService;
import bank.services.dbServices.OrganisationDaoService;
import bank.services.dbServices.TransactionDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * This service performs all things that can do simple user in web banking.
 */
@Service
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class UserAccountService {
    private OrganisationDaoService organisationDaoService;
    private BankAccountDaoService bankAccountDaoService;
    private TransactionDaoService transactionDaoService;
    private RequestValidator requestValidator;
    private AuthenticationHelperService authenticationHelperService;

    private static final String NBRB_RATES_URL = "http://www.nbrb.by/API/ExRates/Rates/";

    @Autowired
    public UserAccountService(OrganisationDaoService organisationDaoService,
                              BankAccountDaoService bankAccountDaoService,
                              TransactionDaoService transactionDaoService,
                              RequestValidator requestValidator,
                              AuthenticationHelperService authenticationHelperService) {
        this.organisationDaoService = organisationDaoService;
        this.bankAccountDaoService = bankAccountDaoService;
        this.transactionDaoService = transactionDaoService;
        this.requestValidator = requestValidator;
        this.authenticationHelperService = authenticationHelperService;
    }

    /**
     * This method transfers money from the user account to the organisation.
     * @param sourceBankAccountId - id of user bank account, that sanding money to another user.
     * @param destinationOrganisationId - id of organisation that accepts money form user.
     * @param moneyToAdd - how much money user sends to another user.
     */
    public synchronized void doPayment(Long sourceBankAccountId, Long destinationOrganisationId, Integer moneyToAdd) {
        BankAccount sourceBankAccount = bankAccountDaoService.getBankAccountById(sourceBankAccountId);
        sourceBankAccount.takeMoney(moneyToAdd);

        BankAccount targetBankAccount = organisationDaoService.getOrganisationsById(destinationOrganisationId)
                .getBankAccountList().get(0);
        targetBankAccount.addMoney(moneyToAdd);

        Transaction transaction = new Transaction(sourceBankAccount, targetBankAccount, moneyToAdd);
        transactionDaoService.createTransaction(transaction);

        bankAccountDaoService.updateBankAccount(sourceBankAccount);
        bankAccountDaoService.updateBankAccount(targetBankAccount);
    }

    /**
     * This method prepares payment request by calling validation method and getting all needed info from request.
     */
    public void prepareForPayment(HttpServletRequest request) throws Exception {
        requestValidator.isValidPayment(request);
        Long sourceBankAccountId = Long.parseLong(request.getParameter("bankAccounts"));
        Long destinationOrganisationId = Long.parseLong(request.getParameter("organisation"));
        Integer moneyToAdd = Integer.parseInt(request.getParameter("money_count"));
        doPayment(sourceBankAccountId, destinationOrganisationId, moneyToAdd);
    }

    /**
     * This method transfers money direct from the user account to the another user bank account
     * @param sourceBankAccountId - id of user bank account, that sanding money to another user.
     * @param destinationBankAccountCardNumber - card number of bank account, that accepts money form another user.
     * @param moneyValue - how much money user sends to another user.
     */
    public void doTransaction(Long sourceBankAccountId, String destinationBankAccountCardNumber, Integer moneyValue) {
        BankAccount sourceBankAccount = bankAccountDaoService.
                getBankAccountById(sourceBankAccountId);
        BankAccount destinationBankAccount = bankAccountDaoService.
                getBankAccountByCardNumber(destinationBankAccountCardNumber);

        sourceBankAccount.takeMoney(moneyValue);
        destinationBankAccount.addMoney(moneyValue);

        bankAccountDaoService.updateBankAccount(sourceBankAccount);
        bankAccountDaoService.updateBankAccount(destinationBankAccount);

        Transaction transaction = new Transaction(sourceBankAccount,
                destinationBankAccount,
                moneyValue);
        transactionDaoService.createTransaction(transaction);
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

    public void addOrganisation(Long bankAccountId, String organisationName) {
        BankAccount bankAccount = bankAccountDaoService.getBankAccountById(bankAccountId);

        Organisations organisation = new Organisations();
        organisation.setName(organisationName);
        organisationDaoService.save(organisation);

        bankAccount.setOrganisation(organisation);
        bankAccountDaoService.updateBankAccount(bankAccount);
    }

    /**
     * This method adding new bank account for authenticated user
     */
    public void addBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setMoney(0.);
        bankAccount.setUser(authenticationHelperService.getAuthenticatedUser(false));
        BankCardNumberGenerator generator = new BankCardNumberGenerator();
        bankAccount.setCardNumber(generator.generate("21", 16));
        bankAccountDaoService.saveBankAccount(bankAccount);
    }

    /**
     * This method just adding money to specified bank account.
     * @param bankAccountID - ID of bank account, that we need to sand money.
     * @param moneyToAdd - how much money wee need to send.
     */
    public void addMoney(Long bankAccountID, Integer moneyToAdd) {
        BankAccount bankAccount = bankAccountDaoService.getBankAccountById(bankAccountID);
        bankAccount.addMoney(moneyToAdd);
        bankAccountDaoService.saveBankAccount(bankAccount);
    }
}
