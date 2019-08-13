package bank.services;

import bank.model.entity.BankAccount;
import bank.model.entity.Role;
import bank.model.entity.User;
import bank.model.entity.UserRole;
import bank.services.dbServices.BankAccountDaoService;
import bank.services.dbServices.RoleDaoService;
import bank.services.dbServices.UserDaoService;
import bank.services.dbServices.UserRoleDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminAccountService {

    private UserDaoService userDaoService;
    private RoleDaoService roleDaoService;
    private UserRoleDaoService userRoleDaoService;

    @Autowired
    public AdminAccountService(UserDaoService userDaoService,
                               RoleDaoService roleDaoService,
                               UserRoleDaoService userRoleDaoService) {
        this.userDaoService = userDaoService;
        this.roleDaoService = roleDaoService;
        this.userRoleDaoService = userRoleDaoService;
    }



    /**
     * This method returning user object to user update view by selected user ID.
     * @param selectedUserId - ID of a user that wee need to update.
     * @return user to update object.
     */
    public User getUserToUpdate(Long selectedUserId) {
        User user = new User();
        if (selectedUserId > 0 && selectedUserId <= userDaoService.getUsers().size()) {
            user = userDaoService.getUserById(selectedUserId);
        }
        return user;
    }

    /**
     * This method deleting specified user.
     * @param userId - ID of user, that wee need to delete.
     */
    public void deleteUser(Long userId) {
        User user = userDaoService.getUserById(userId);
        userDaoService.deleteUser(user);
    }

    /**
     * Updating user in database by calling UserDaoService method
     */

    /**
     * Method updating user with new specified parameters.
     * @param userToUpdateId - ID of a user that wee need to update.
     * @param newUsername - new username of a user.
     * @param newPassword - new password of a user.
     */
    public void updateUser(Long userToUpdateId, String newUsername, String newPassword) {
        User userToUpdate = userDaoService.getUserById(userToUpdateId);

        if (!newUsername.isEmpty() || !newPassword.isEmpty()) {
            userToUpdate.setUsername(newUsername);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);

            userToUpdate.setPassword(encoder.encode(newPassword));
            userDaoService.updateUser(userToUpdate);
        }
    }

    /**
     * This method setting role for a user.
     * @param userId - ID of user, that wee need to set a role.
     * @param roleName - name of a role that wee need to set.
     */
    public void setRoleForUser(Long userId, String roleName) {
        User user = userDaoService.getUserById(userId);
        Role role = roleDaoService.getRoleByName(roleName);
        UserRole userRole = new UserRole(user, role);
        userRoleDaoService.save(userRole);
    }
}
