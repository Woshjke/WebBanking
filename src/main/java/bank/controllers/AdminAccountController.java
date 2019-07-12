package bank.controllers;

import bank.database.entity.User;
import bank.services.AdminAccountService;
import bank.services.dbServices.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static bank.PageNameConstants.*;


@Controller
public class AdminAccountController {

    private UserDaoService userDaoService;
    private AdminAccountService adminService;

    @Autowired
    public AdminAccountController(UserDaoService userDaoService, AdminAccountService adminService) {
        this.userDaoService = userDaoService;
        this.adminService = adminService;
    }


    @RequestMapping(value = "/admin_page", method = RequestMethod.GET)
    public ModelAndView getAdminPage() {
        return new ModelAndView(ADMIN_PAGE);
    }

    @RequestMapping(value = "/admin_page", method = RequestMethod.POST)
    public ModelAndView getAdminPagePost() {
        return new ModelAndView(ADMIN_PAGE);
    }


    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public ModelAndView createUser() {
        return new ModelAndView(CREATE_USER_PAGE);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RedirectView registerUser(HttpServletRequest request) {
        adminService.registerUser(request);
        return new RedirectView(ADMIN_PAGE);
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ModelAndView updateUser(HttpServletRequest request) {
        ModelAndView mnv = new ModelAndView(UPDATE_USER_PAGE);
        List<User> userList = userDaoService.getUsers();
        User userToUpdate = adminService.getUserToUpdate(request);
        mnv.addObject("userToUpdate", userToUpdate);
        mnv.addObject("usersList", userList);
        return mnv;
    }

    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    public RedirectView doUpdate(HttpServletRequest request) {
        adminService.updateUser(request);
        return new RedirectView(ADMIN_PAGE);
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public ModelAndView deleteUser(HttpServletRequest request) {
        ModelAndView mnv = new ModelAndView(DELETE_USER_PAGE);
        adminService.deleteUser(request);
        List<User> userList = userDaoService.getUsers();
        mnv.addObject("usersList", userList);
        return mnv;
    }

    @ModelAttribute("user")
    public User setSignUpForm() {
        return new User();
    }
}
