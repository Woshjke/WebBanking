package bank.services.dbServices;

import bank.model.repositories.UserRepository;
import bank.model.dto.BankAccountDTO;
import bank.model.dto.UserDTO;
import bank.model.entity.BankAccount;
import bank.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDaoService {

    private UserRepository userRepository;

    @Autowired
    public UserDaoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Saving or updating user in database
     * @param user - user object to save
     */
    public void createUser(User user) {
        userRepository.save(user);
    }

    /**
     * Getting users list from database
     * @return list of users
     */
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    /**
     * Getting user from database by ID
     * @param id - user ID
     * @return user object
     */
    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Getting user form database by username
     * @param username - user username
     * @return user object
     */
    public User getUserByUsername(String username) {return userRepository.findByUsername(username);}

    /**
     * Updating user in database and, if password was changed, hashing password
     * @param user - user to update
     */
    public void updateUser(User user) {
        User userInDB = userRepository.findById(user.getId());
        if (!userInDB.getPassword().equals(user.getPassword())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
            user.setPassword(encoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    /**
     * Deleting user form database
     * @param user - user to delete
     */
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    /**
     * This method getting list of users form database and converting it to list of user DTOs
     * @return list of user DTOs
     */
    public List<UserDTO> getUserDtoList() {
        List<User> users = (List<User>) userRepository.findAll();
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
        User user = userRepository.findByUsername(username);
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword());
    }

    /**
     * THis method getting bank accounts list of specific user from database
     * and converting it to list of bank accounts DTO
     * @param username - user username
     * @return list of bank accounts DTO
     */
    public List<BankAccountDTO> getBankAccountDtoListByUsername(String username) {
        User user = userRepository.findByUsername(username);
        List<BankAccount> userBankAccounts = user.getBankAccounts();
        return userBankAccounts.stream()
                .map(i -> new BankAccountDTO(i.getId(),
                        i.getMoney()))
                .collect(Collectors.toList());
    }

    public List<User> getUserListByStatus(String status) {
        return userRepository.getAllByStatus(status);
    }



}
