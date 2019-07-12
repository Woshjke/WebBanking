package bank.services.dbServices;

import bank.database.dao.UserRepository;
import bank.database.dto.UserDTO;
import bank.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserDaoService {

    private UserRepository userDao;

    @Autowired
    public UserDaoService(UserRepository userDao) {
        this.userDao = userDao;
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
        List<User> userList = (List<User>) userDao.findAll();
        return userList.stream()
                .map(i -> new UserDTO(i.getId(), i.getUsername(), i.getPassword()))
                .collect(Collectors.toList());
    }

    public UserDTO getUserDtoByUsername(String username) {
        User user = userDao.findByUsername(username);
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword());
    }

}
