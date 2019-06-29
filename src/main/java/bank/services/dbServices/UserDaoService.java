package bank.services.dbServices;

import bank.database.dao.UserDao;
import bank.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserDaoService {

    private final UserDao userDao;

    @Autowired
    public UserDaoService(UserDao userDao) {
        this.userDao = userDao;
    }


    public void createUser(User user) {
        userDao.createUser(user);
    }

    public List<User> getUsers() {
        return userDao.getUserList();
    }

    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    public User getUserByUsername(String username) {return userDao.getUserByUsername(username);}

}
