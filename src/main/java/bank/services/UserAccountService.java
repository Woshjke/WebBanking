package bank.services;

import bank.model.entity.BankAccount;
import bank.model.entity.Role;
import bank.model.entity.Transaction;
import bank.model.entity.User;
import bank.services.dbServices.BankAccountDaoService;
import bank.services.dbServices.OrganisationDaoService;
import bank.services.dbServices.TransactionDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Service
@Transactional
public class UserAccountService {
    private OrganisationDaoService organisationService;
    private BankAccountDaoService bankAccountDaoService;
    private TransactionDaoService transactionDaoService;

    private static final String NBRB_RATES_URL = "http://www.nbrb.by/API/ExRates/Rates/";

    @Autowired
    public UserAccountService(OrganisationDaoService organisationService,
                              BankAccountDaoService bankAccountDaoService,
                              TransactionDaoService transactionDaoService) {
        this.organisationService = organisationService;
        this.bankAccountDaoService = bankAccountDaoService;
        this.transactionDaoService = transactionDaoService;
    }

    /**
     * This method transfers money from the user account to the organization account
     *
     */
    //todo поменить аккаунт для входящих денег флагом. useForDefaultIncomingPayment
    public void doPayment(Long sourceBankAccountId, Long destinationOrganisationId, Integer moneyToAdd) {
            BankAccount sourceBankAccount = bankAccountDaoService.getBankAccountById(sourceBankAccountId);
            sourceBankAccount.takeMoney(moneyToAdd);

            BankAccount orgBankAccount = organisationService.getOrganisationsById(destinationOrganisationId)
                    .getBankAccountList().get(0);
            orgBankAccount.addMoney(moneyToAdd);

            Transaction transaction = new Transaction(sourceBankAccount, orgBankAccount, moneyToAdd);
            transactionDaoService.createTransaction(transaction);

            bankAccountDaoService.updateBankAccount(sourceBankAccount);
            bankAccountDaoService.updateBankAccount(orgBankAccount);
    }

    /**
     * This method transfers money direct from the user account to the another user account
     *
     * @return was transaction completed or not
     */
    public void doTransaction(Long sourceBankAccountId, Long destinationBankAccountId, Integer moneyValue) {
        BankAccount sourceBankAccount = bankAccountDaoService.
                getBankAccountById(sourceBankAccountId);
        BankAccount destinationBankAccount = bankAccountDaoService.
                getBankAccountById(destinationBankAccountId);

        sourceBankAccount.takeMoney(moneyValue);
        destinationBankAccount.addMoney(moneyValue);

        bankAccountDaoService.updateBankAccount(sourceBankAccount);
        bankAccountDaoService.updateBankAccount(destinationBankAccount);

        Transaction transaction = new Transaction(sourceBankAccount,
                destinationBankAccount,
                moneyValue);
        transactionDaoService.createTransaction(transaction);
    }

    //todo переделать и встроить в проект
    public boolean addOrganisation(HttpServletRequest request) {
        // TODO: 23.07.2019 Проверить пренадлежит ли аккаунт залогиненому пользователю
        Long selectedBankAccountID;
        try {
            selectedBankAccountID = Long.valueOf(request.getParameter("bankAccount"));
        } catch (NumberFormatException ex) {
            return false;
        }
        BankAccount bankAccount = bankAccountDaoService.getBankAccountById(selectedBankAccountID);

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

    public void addMoney(Long bankAccountID, Integer moneyToAdd) {
        BankAccount bankAccount = bankAccountDaoService.getBankAccountById(bankAccountID);
        bankAccount.addMoney(moneyToAdd);
        bankAccountDaoService.saveBankAccount(bankAccount);
    }
}
