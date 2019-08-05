package bank.services;

import bank.RequestValidator;
import bank.model.entity.BankAccount;
import bank.model.entity.Role;
import bank.model.entity.User;
import bank.services.dbServices.BankAccountDaoService;
import bank.services.dbServices.RoleDaoService;
import bank.services.dbServices.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AdminAccountService {

    private UserDaoService userDaoService;
    private RoleDaoService roleDaoService;
    private BankAccountDaoService bankAccountDaoService;

    @Autowired
    public AdminAccountService(UserDaoService userDaoService,
                               RoleDaoService roleDaoService,
                               BankAccountDaoService bankAccountDaoService) {
        this.userDaoService = userDaoService;
        this.roleDaoService = roleDaoService;
        this.bankAccountDaoService = bankAccountDaoService;
    }

    /**
     * This method returning user object to user update view by selected user id
     *
     * @return user object
     */
    public User getUserToUpdate(Long selectedUserId) {
        User user = new User();
        if (selectedUserId > 0 && selectedUserId <= userDaoService.getUsers().size()) {
            user = userDaoService.getUserById(selectedUserId);
        }
        return user;
    }

    /**
     * deleting user, selected in JSP, by calling UserDaoService method
     *
     * @param userId - ID of the user you want to delete
     * @return user was deleted or not
     */
    public void deleteUser(Long userId) {
        User user = userDaoService.getUserById(userId);
        userDaoService.deleteUser(user);
    }

    /**
     * Registering user in system by calling UserDaoService method
     */
    public void registerUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder(11).encode(password));

        Set<Role> userRoles = new HashSet<>();
        List<Role> roles = roleDaoService.getRoles();
        for (Role iter : roles) {
            if (iter.getName().equals("ROLE_USER")) {
                userRoles.add(iter);
            }
        }
        user.setRoles(userRoles);
        user.setStatus("disabled");

        userDaoService.createUser(user);
        // TODO: 03.08.2019 Потом убрать это
//        user = userDaoService.getUserByUsername(username);

//        BankAccount bankAccount = new BankAccount();
//        bankAccount.setMoney(0.);
//        bankAccount.setUser(user);
//
//        bankAccountDaoService.saveBankAccount(bankAccount);
//
//        List<BankAccount> bankAccountList = new ArrayList<>();
//        bankAccountList.add(bankAccount);
//        user.setBankAccounts(bankAccountList);

//        userDaoService.updateUser(user);
//        bankAccountDaoService.updateBankAccount(bankAccount);

    }

    /**
     * Updating user in database by calling UserDaoService method
     */
    public void updateUser(Long userToUpdateId, String newUsername, String newPassword) {
        User userToUpdate = userDaoService.getUserById(userToUpdateId);

        if (!newUsername.isEmpty() || !newPassword.isEmpty()) {
            userToUpdate.setUsername(newUsername);
            userToUpdate.setPassword(newPassword);
            userDaoService.updateUser(userToUpdate);
        }
    }


    public boolean addBankAccount(Long userID) {
        User user = userDaoService.getUserById(userID);
        BankAccount newBankAccount = new BankAccount();
        newBankAccount.setMoney(0.0);
        newBankAccount.setUser(user);
        bankAccountDaoService.saveBankAccount(newBankAccount);
        return true;
    }

    public void activateAccount(Long userId) {
        User user = userDaoService.getUserById(userId);
        user.setStatus("active");
        userDaoService.updateUser(user);
    }


}
