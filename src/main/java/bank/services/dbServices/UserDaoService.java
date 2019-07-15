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

    public void createUser(User user) {
        userDao.save(user);
    }

    public List<User> getUsers() {
        return (List<User>) userDao.findAll();
    }

    public User getUserById(Long id) {
        return userDao.findById(id);
    }

    public void updateUser(User user) {
        User userInDB = userDao.findById(user.getId());
        if (!userInDB.getPassword().equals(user.getPassword())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
            user.setPassword(encoder.encode(user.getPassword()));
        }
        userDao.save(user);
    }

    public void deleteUser(User user) {
        userDao.delete(user);
    }

    public User getUserByUsername(String username) {return userDao.findByUsername(username);}

    public List<UserDTO> getUserDtoList() {
        List<User> users = (List<User>) userDao.findAll();
        return users.stream()
                .map(i -> new UserDTO(i.getId(), i.getUsername(), i.getPassword()))
                .collect(Collectors.toList());
    }

    public UserDTO getUserDtoByUsername(String username) {
        User user = userDao.findByUsername(username);
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword());
    }

    public List<BankAccountDTO> getBankAccountDTOList() {
        List<BankAccount> bankAccounts = (List<BankAccount>) bankAccountRepository.findAll();
        return bankAccounts.stream()
                .map(i -> new BankAccountDTO(i.getId(),
                                             i.getMoney()))
                .collect(Collectors.toList());
    }

    public List<BankAccountDTO> getBankAccountsByUsername(String username) {
        User user = userDao.findByUsername(username);
        List<BankAccount> userBankAccounts = user.getBankAccounts();
        return userBankAccounts.stream()
                .map(i -> new BankAccountDTO(i.getId(),
                        i.getMoney()))
                .collect(Collectors.toList());
    }

}
