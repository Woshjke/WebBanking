package bank.controllers;

import bank.database.entity.User;
import bank.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminAccountController {

    private UserService userService;

    @Autowired
    public AdminAccountController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public String createUser() {
        return "createUserPage";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute(value = "user") User user) {
        userService.createUser(user);
        return "adminPage";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(HttpServletRequest request, ModelMap model) {
        //todo допилить логику - бывают вылеты из-за несуществуещего id
        if (request.getParameter("userId") != null && !request.getParameter("userId").equals("0")) {
            Long userId = Long.parseLong(request.getParameter("userId"));
            User user = userService.getUserGyId(userId);
            System.out.println(user.getLogin());
            model.addAttribute("userToUpdate", user);
        }
        return "updateUserPage";
    }

    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    public String doUpdate(@ModelAttribute("userToUpdate") User user) {
        userService.updateUser(user);
        return "adminPage";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public String deleteUser(HttpServletRequest request) {
        //todo допилить логику - бывают вылеты из-за несуществуещего id
        if (request.getParameter("userId") != null && !request.getParameter("userId").equals("0")) {
            User user = userService.getUserGyId(Long.parseLong(request.getParameter("userId")));
            userService.deleteUser(user);
            return "adminPage";
        }
        return "deleteUserPage";
    }

    @ModelAttribute("user")
    public User setSignUpForm() {
        return new User();
    }
}
