package bank.controllers;

import bank.model.dto.UserDTO;
import bank.model.entity.User;
import bank.services.AdminAccountService;
import bank.services.dbServices.UserDaoService;
import bank.services.responses.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static bank.PageNameConstants.*;

@RestController
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

    @RequestMapping(value = "/readUsers", method = RequestMethod.GET)
    public ModelAndView readUsers() {
        return new ModelAndView(READ_USERS_PAGE);
    }

    @RequestMapping(value = "/filterUsers", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getAllUsers(@RequestParam String myInfo) {
        if (myInfo.equals("")) {
            List<UserDTO> users = userDaoService.getUserDtoList();
            ServiceResponse<List<UserDTO>> response = new ServiceResponse<>("success", users);
            System.out.println(myInfo);
            return ResponseEntity.ok(response);
        } else {
            List<UserDTO> userDTOS = new ArrayList<>();
            userDTOS.add(userDaoService.getUserDtoByUsername(myInfo));
            ServiceResponse<List<UserDTO>> response = new ServiceResponse<>("success", userDTOS);
            System.out.println(myInfo);
            return ResponseEntity.ok(response);
        }
    }

    @ModelAttribute("user")
    public User setSignUpForm() {
        return new User();
    }
}
