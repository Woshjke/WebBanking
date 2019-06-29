package bank.services;

import bank.database.entity.User;
import bank.services.dbServices.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AdminService {

    private UserDaoService userDaoService;

    @Autowired
    public AdminService(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    public User updateUser(HttpServletRequest request) {
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

    public void deleteUser(HttpServletRequest request) {
        if (request.getParameter("users") != null && !request.getParameter("users").equals("0")) {
            User user = userDaoService.getUserById(Long.parseLong(request.getParameter("users")));
            userDaoService.deleteUser(user);
        }
    }
}
