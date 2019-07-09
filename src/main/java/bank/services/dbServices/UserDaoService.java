package bank.services.dbServices;

import bank.database.dao.UserRepository;
import bank.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

}
