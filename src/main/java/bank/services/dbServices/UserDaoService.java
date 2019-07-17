package bank.services.dbServices;

import bank.model.dao.BankAccountRepository;
import bank.model.dao.UserRepository;
import bank.model.dto.BankAccountDTO;
import bank.model.dto.UserDTO;
import bank.model.entity.BankAccount;
import bank.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserDaoService {

    private UserRepository userDao;
    private BankAccountRepository bankAccountRepository;

    @Autowired
    public UserDaoService(UserRepository userDao, BankAccountRepository bankAccountRepository) {
        this.userDao = userDao;
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Saving or updating user in database
     * @param user - user object to save
     */
    public void createUser(User user) {
        userDao.save(user);
    }

    /**
     * Getting users list from database
     * @return list of users
     */
    public List<User> getUsers() {
        return (List<User>) userDao.findAll();
    }

    /**
     * Getting user from database by ID
     * @param id - user ID
     * @return user object
     */
    public User getUserById(Long id) {
        return userDao.findById(id);
    }

    /**
     * Getting user form database by username
     * @param username - user username
     * @return user object
     */
    public User getUserByUsername(String username) {return userDao.findByUsername(username);}

    /**
     * Updating user in database and, if password was changed, hashing password
     * @param user - user to update
     */
    public void updateUser(User user) {
        User userInDB = userDao.findById(user.getId());
        if (!userInDB.getPassword().equals(user.getPassword())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
            user.setPassword(encoder.encode(user.getPassword()));
        }
        userDao.save(user);
    }

    /**
     * Deleting user form database
     * @param user - user to delete
     */
    public void deleteUser(User user) {
        userDao.delete(user);
    }

    /**
     * This method getting list of users form database and converting it to list of user DTOs
     * @return list of user DTOs
     */
    public List<UserDTO> getUserDtoList() {
        List<User> users = (List<User>) userDao.findAll();
        return users.stream()
                .map(i -> new UserDTO(i.getId(), i.getUsername(), i.getPassword()))
                .collect(Collectors.toList());
    }

    /**
     * THis method getting user from database by username and converting it to user DTO
     * @param username - user username
     * @return user DTO
     */
    public UserDTO getUserDtoByUsername(String username) {
        User user = userDao.findByUsername(username);
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword());
    }

    /**
     * This method getting list of bank accounts form database and converting it to list of bank accounts DTOs
     * @return list of bank accounts DTOs
     */
    public List<BankAccountDTO> getBankAccountDTOList() {
        List<BankAccount> bankAccounts = (List<BankAccount>) bankAccountRepository.findAll();
        return bankAccounts.stream()
                .map(i -> new BankAccountDTO(i.getId(),
                                             i.getMoney()))
                .collect(Collectors.toList());
    }

    /**
     * THis method getting bank accounts list of specific user from database
     * and converting it to list of bank accounts DTO
     * @param username - user username
     * @return list of bank accounts DTO
     */
    public List<BankAccountDTO> getBankAccountsByUsername(String username) {
        User user = userDao.findByUsername(username);
        List<BankAccount> userBankAccounts = user.getBankAccounts();
        return userBankAccounts.stream()
                .map(i -> new BankAccountDTO(i.getId(),
                        i.getMoney()))
                .collect(Collectors.toList());
    }

}
