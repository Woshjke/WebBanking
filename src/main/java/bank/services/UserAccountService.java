package bank.services;

import bank.database.entity.BankAccount;
import bank.database.entity.User;
import bank.services.dbServices.BankAccountService;
import bank.services.dbServices.OrganisationDaoService;
import bank.services.dbServices.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static bank.PageNameConstants.*;

@Service
public class UserAccountService {

    private OrganisationDaoService organisationService;
    private UserDaoService userDaoService;
    private BankAccountService bankAccountService;

    @Autowired
    public UserAccountService(OrganisationDaoService organisationService, UserDaoService userDaoService, BankAccountService bankAccountService) {
        this.organisationService = organisationService;
        this.userDaoService = userDaoService;
        this.bankAccountService = bankAccountService;
    }


    public void doPayment(Long selectedOrg, Integer moneyToAdd, Long selectedBankAccount) {
        // TODO: 28.06.2019 BigDecimal
        // TODO: 29.06.2019 Код - кусок ***
        // TODO: 04.07.2019 Поместить в DAO
        User currentUser = getAuthenticatedUser();
        BankAccount userBankAccount = bankAccountService.getBankAccountById(selectedBankAccount);

        if (userBankAccount.getMoney() < moneyToAdd) {
            return;
        }

        Double currentUserMoney = userBankAccount.getMoney();
        userBankAccount.setMoney(currentUserMoney - moneyToAdd);

        Long userId = organisationService.getOrgById(selectedOrg)
                .getBankAccountList().get(0)
                .getUser().getId();

        User user = userDaoService.getUserById(userId);
        BankAccount orgBankAccount = new ArrayList<>(user.getBankAccounts()).get(0);
        Double orgCurrentMoney = orgBankAccount.getMoney();
        orgBankAccount.setMoney(orgCurrentMoney + moneyToAdd);

        bankAccountService.updateBankAccount(userBankAccount);
        bankAccountService.updateBankAccount(orgBankAccount);
    }

    public User getAuthenticatedUser() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userDaoService.getUserByUsername(username);
    }

}
