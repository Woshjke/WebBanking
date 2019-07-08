package bank.controllers;

import bank.database.entity.User;
import bank.services.AdminService;
import bank.services.dbServices.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static bank.PageNameConstants.*;


// TODO: 29.06.2019 Вся админка сломана из-за новой схемы БД. Чинить !
@Controller
public class AdminAccountController {

    private UserDaoService userDaoService;
    private AdminService adminService;

    @Autowired
    public AdminAccountController(UserDaoService userDaoService, AdminService adminService) {
        this.userDaoService = userDaoService;
        this.adminService = adminService;
    }


    @RequestMapping(value = "/admin_page", method = RequestMethod.GET)
    public String getAdminPage() {
        return ADMIN_PAGE;
    }

    @RequestMapping(value = "/admin_page", method = RequestMethod.POST)
    public String getAdminPagePost() {
        return ADMIN_PAGE;
    }


    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public String createUser() {
        return CREATE_USER_PAGE;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(HttpServletRequest request) {
        adminService.registerUser(request);
        return ADMIN_PAGE;
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(HttpServletRequest request, ModelMap map) {
        List<User> userList = userDaoService.getUsers();
        User userToUpdate = adminService.getUserToUpdate(request);
        map.addAttribute("userToUpdate", userToUpdate);
        map.addAttribute("usersList", userList);
        return UPDATE_USER_PAGE;
    }

    // TODO: 08.07.2019 Перенести в сервис
    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    public String doUpdate(HttpServletRequest request) {
        Long userToUpdateId = Long.parseLong(request.getParameter("id"));
        User userToUpdate = userDaoService.getUserById(userToUpdateId);
        String newUsername = request.getParameter("username");
        String newPassword = request.getParameter("password");
        if (!newUsername.isEmpty() || !newPassword.isEmpty()) {
            userToUpdate.setUsername(newUsername);
            userToUpdate.setPassword(new BCryptPasswordEncoder(11).encode(newPassword));
            userDaoService.updateUser(userToUpdate);
        }
        return ADMIN_PAGE;
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public String deleteUser(HttpServletRequest request, ModelMap map) {
        adminService.deleteUser(request);
        List<User> userList = userDaoService.getUsers();
        map.addAttribute("usersList", userList);
        return DELETE_USER_PAGE;
    }

    @ModelAttribute("user")
    public User setSignUpForm() {
        return new User();
    }
}
