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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static bank.PageNameConstants.*;

@Service
public class UserService {

    private OrganisationDaoService organisationService;
    private UserDaoService userDaoService;
    private BankAccountService bankAccountService;

    @Autowired
    public UserService(OrganisationDaoService organisationService, UserDaoService userDaoService, BankAccountService bankAccountService) {
        this.organisationService = organisationService;
        this.userDaoService = userDaoService;
        this.bankAccountService = bankAccountService;
    }



    public void doPayment(Long selectedOrg, Integer moneyToAdd, Long selectedBankAccount) {
        // TODO: 28.06.2019 BigDecimal
        // TODO: 29.06.2019 Код - кусок говна. Может заменить Set на что-то другое ??
        User currentUser = getAuthenticatedUser();
        BankAccount userBankAccount = bankAccountService.getBankAccountById(selectedBankAccount);


        if (userBankAccount.getMoney() < moneyToAdd) {
            return;
        }

        Integer currentUserMoney = userBankAccount.getMoney();
        userBankAccount.setMoney(currentUserMoney - moneyToAdd);

        Long userId = organisationService.getOrgById(selectedOrg).getUser_id();
        User user = userDaoService.getUserById(userId);
        BankAccount orgBankAccount = new ArrayList<>(user.getBankAccounts()).get(0);
        Integer orgCurrentMoney = orgBankAccount.getMoney();
        orgBankAccount.setMoney(orgCurrentMoney + moneyToAdd);

        bankAccountService.updateBankAccount(userBankAccount);
        bankAccountService.updateBankAccount(orgBankAccount);
    }

    public String login(User user) {
        User userFromDb = userDaoService.getUserByUsername(user.getUsername());
        if (user.getPassword().equals(userFromDb.getPassword())) {
            if (userFromDb.isAdmin()) {
                return ADMIN_PAGE;
            } else {
                return USER_PAGE;
            }
        }   return LOGIN_PAGE;
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
