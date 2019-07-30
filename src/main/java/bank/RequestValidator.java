package bank;

import bank.model.entity.BankAccount;
import bank.services.dbServices.BankAccountDaoService;
import bank.services.dbServices.OrganisationDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class RequestValidator {

    private BankAccountDaoService bankAccountDaoService;
    private OrganisationDaoService organisationDaoService;
    private AuthenticationHelper authenticationHelper;

    @Autowired
    public RequestValidator(BankAccountDaoService bankAccountDaoService,
                            OrganisationDaoService organisationDaoService,
                            AuthenticationHelper authenticationHelper) {
        this.bankAccountDaoService = bankAccountDaoService;
        this.organisationDaoService = organisationDaoService;
        this.authenticationHelper = authenticationHelper;
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

        if (moneyToAdd < 0) {
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
}
