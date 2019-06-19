package bank.database.service;

import bank.database.dao.UserDao;
import bank.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void createUser(User user) {
        userDao.createUser(user);
    }

    @Transactional
    public List<User> getUsers() {
        return userDao.getUserList();
    }

    @Transactional
    public User getUserGyId(Long id) {
        return userDao.getUserById(id);
    }

    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Transactional
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }
}
