package bank.services;

import bank.model.entity.BankAccount;
import bank.model.entity.Role;
import bank.model.entity.User;
import bank.services.dbServices.BankAccountService;
import bank.services.dbServices.RoleDaoService;
import bank.services.dbServices.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminAccountService {

    private UserDaoService userDaoService;
    private RoleDaoService roleDaoService;
    private BankAccountService bankAccountService;

    @Autowired
    public AdminAccountService(UserDaoService userDaoService,
                               RoleDaoService roleDaoService,
                               BankAccountService bankAccountService) {
        this.userDaoService = userDaoService;
        this.roleDaoService = roleDaoService;
        this.bankAccountService = bankAccountService;
    }


    /**
     * This method returning user object to user update view by selected user id
     * @param request - request from JSP with needed params (user id)
     * @return user object
     */
    public User getUserToUpdate(HttpServletRequest request) {
        long selectedUserId = 0L;
        if (request.getParameter("users") != null && !request.getParameter("users").equals("0")) {
            selectedUserId = Long.parseLong(request.getParameter("users"));
        }
        User user = new User();
        if (selectedUserId > 0) {
            user = userDaoService.getUserById(selectedUserId);
        }
        return user;
    }

    /**
     * deleting user, selected in JSP, by calling SserDaoService method
     * @param request - request form JSP with needed params (user id)
     * @return user was deleted or not
     */
    public boolean deleteUser(HttpServletRequest request) {
        if (request.getParameter("users") != null && !request.getParameter("users").equals("0")) {
            User user = userDaoService.getUserById(Long.parseLong(request.getParameter("users")));
            userDaoService.deleteUser(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Registering user in system by calling UserDaoService method
     * @param request - request from JSP with needed params (username, password)
     */
    public void registerUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder(11).encode(password));
        user.setPassword(password);

        Set<Role> userRoles = new HashSet<>();
        List<Role> roles = roleDaoService.getRoles();
        for (Role iter : roles) {
            if (iter.getName().equals("ROLE_USER")) {
                userRoles.add(iter);
            }
        }
        user.setRoles(userRoles);

        userDaoService.createUser(user);
        user = userDaoService.getUserByUsername(username);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setMoney(0.);
        bankAccount.setUser(user);

        bankAccountService.saveBankAccount(bankAccount);

        List<BankAccount> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount);
        user.setBankAccounts(bankAccountList);

        userDaoService.updateUser(user);
        bankAccountService.updateBankAccount(bankAccount);
    }

    /**
     * Updating user in database by calling UserDaoService method
     * @param request
     */
    public void updateUser(HttpServletRequest request) {
        Long userToUpdateId = Long.parseLong(request.getParameter("id"));
        User userToUpdate = userDaoService.getUserById(userToUpdateId);
        String newUsername = request.getParameter("username");
        String newPassword = request.getParameter("password");
        if (!newUsername.isEmpty() || !newPassword.isEmpty()) {
            userToUpdate.setUsername(newUsername);
            userToUpdate.setPassword(newPassword);
            userDaoService.updateUser(userToUpdate);
        }
    }
}
