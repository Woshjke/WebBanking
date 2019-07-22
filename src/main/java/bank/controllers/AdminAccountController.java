package bank.controllers;

import bank.model.dto.BankAccountDTO;
import bank.model.dto.UserDTO;
import bank.model.entity.BankAccount;
import bank.model.entity.User;
import bank.services.AdminAccountService;
import bank.services.dbServices.BankAccountDaoService;
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

import static bank.ApplicationProperties.ADMIN_PAGE;
import static bank.ApplicationProperties.CREATE_USER_PAGE;
import static bank.ApplicationProperties.DELETE_USER_PAGE;
import static bank.ApplicationProperties.READ_BANK_ACCOUNTS;
import static bank.ApplicationProperties.READ_USERS_PAGE;
import static bank.ApplicationProperties.UPDATE_USER_PAGE;


/**
 * Admin features controller
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminAccountController {

    private UserDaoService userDaoService;
    private AdminAccountService adminService;
    private BankAccountDaoService bankAccountDaoService;

    @Autowired
    public AdminAccountController(UserDaoService userDaoService,
                                  AdminAccountService adminService,
                                  BankAccountDaoService bankAccountDaoService) {
        this.userDaoService = userDaoService;
        this.adminService = adminService;
        this.bankAccountDaoService = bankAccountDaoService;
    }

    /**
     * GET-request of admin page
     * @return admin page view
     */
    @RequestMapping(value = "/admin_page", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showAdminPage(HttpServletRequest request) {
        ModelAndView mnv = new ModelAndView(ADMIN_PAGE);
        String resultMessage = request.getParameter("resultMessage");
        mnv.addObject("resultMessage", resultMessage);
        return mnv;
    }

    /**
     * Handling request of getting user registration page
     * @return user registration view
     */
    @RequestMapping(value = "/createUser", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showCreateUserPage() {
        return new ModelAndView(CREATE_USER_PAGE);
    }

    /**
     * handling user registration request and calls registration service
     * @param request - request from JSP
     * @return admin page view
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RedirectView doCreateUser(HttpServletRequest request) {
        if (adminService.registerUser(request)) {
            return new RedirectView(ADMIN_PAGE + "?resultMessage=Completed");
        } else {
            return new RedirectView(ADMIN_PAGE + "?resultMessage=Failed");
        }
    }

    /**
     * Handling request of getting user update page
     * @param request - request from JSP with selected user param
     * @return user updating view with list of users and selected user (if selected)
     */
    @RequestMapping(value = "/updateUser", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showUpdateUserPage(HttpServletRequest request) {
        ModelAndView mnv = new ModelAndView(UPDATE_USER_PAGE);
        List<User> userList = userDaoService.getUsers();
        User userToUpdate = adminService.getUserToUpdate(request);
        mnv.addObject("userToUpdate", userToUpdate);
        mnv.addObject("usersList", userList);
        return mnv;
    }

    /**
     * handling user updating request and calls user updating service
     * @param request - request from JSP
     * @return admin view page
     */
    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    public RedirectView doUpdateUser(HttpServletRequest request) {
        if (adminService.updateUser(request)) {
            return new RedirectView(ADMIN_PAGE + "?resultMessage=Completed");
        } else {
            return new RedirectView(ADMIN_PAGE + "?resultMessage=Failed");
        }
    }

    /**
     * Handling request of getting user delete page
     * @return user delete page with list of users
     */
    @RequestMapping(value = "/deleteUser", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showDeleteUserPage() {
        ModelAndView mnv = new ModelAndView(DELETE_USER_PAGE);
        List<User> userList = userDaoService.getUsers();
        mnv.addObject("usersList", userList);
        return mnv;
    }

    /**
     * handling user deleting request and calls user delete service
     * @param request - request from jsp with selected user param
     * @return admin page view with result of user deletion message
     */
    @RequestMapping(value = "/doDelete", method = RequestMethod.POST)
    public RedirectView doDeleteUser(HttpServletRequest request) {
        if (adminService.deleteUser(request)) {
           return new RedirectView(ADMIN_PAGE + "?resultMessage=Completed");
        } else {
            return new RedirectView(ADMIN_PAGE + "?resultMessage=Failed");
        }
    }

    /**
     * Handling request of  getting users info reading  page
     * @return user info reading view
     */
    @RequestMapping(value = "/readUsers", method = RequestMethod.GET)
    public ModelAndView readUsers() {
        return new ModelAndView(READ_USERS_PAGE);
    }

    /**
     * Handling request of  getting bank accounts info reading  page
     * @return bank accounts info reading view
     */
    @RequestMapping(value = "/readBankAccounts", method = RequestMethod.GET)
    public ModelAndView readBankAccounts() {
        return new ModelAndView(READ_BANK_ACCOUNTS);
    }

    @RequestMapping(value = "/addMoney", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showAddMoneyPage() {
        ModelAndView mnv = new ModelAndView("add_money_page");
        List<BankAccount> bankAccounts = bankAccountDaoService.getAllBankAccounts();
        mnv.addObject("bankAccounts", bankAccounts);
        return mnv;
    }

    @RequestMapping(value = "/doAddMoney", method = {RequestMethod.POST})
    public RedirectView doAddMoney(HttpServletRequest request) {
        RedirectView rv = new RedirectView();
        if (adminService.addMoney(request)) {
            rv.setUrl(ADMIN_PAGE + "?resultMessage=Operation completed!");
        } else {
            rv.setUrl(ADMIN_PAGE + "?resultMessage=Operation failed!");
        }
        return rv;
    }

    /**
     * Handling AJAX call form users reading view
     * @param username - specific username in
     * @return all users if username field was empty or specific user if username field wasn't empty
     */
    @ResponseBody
    @RequestMapping(value = "/filterUsers", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllUsers(@RequestParam String username) {
        if (username.equals("")) {
            List<UserDTO> users = userDaoService.getUserDtoList();
            ServiceResponse<List<UserDTO>> response = new ServiceResponse<>("success", users);
            return ResponseEntity.ok(response);
        } else {
            List<UserDTO> userDTOS = new ArrayList<>();
            userDTOS.add(userDaoService.getUserDtoByUsername(username));
            ServiceResponse<List<UserDTO>> response = new ServiceResponse<>("success", userDTOS);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Handling AJAX call form bank accounts reading view
     * @param username - specific username in
     * @return all bank accounts if username field was empty
     * or bank accounts of specific user if username field wasn't empty
     */
    @ResponseBody
    @RequestMapping(value = "/filterBankAccounts", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllBankAccounts(@RequestParam String username) {
        if (username.equals("")) {
            List<BankAccountDTO> bankAccounts = userDaoService.getBankAccountDTOList();
            ServiceResponse<List<BankAccountDTO>> response = new ServiceResponse<>("success", bankAccounts);
            return ResponseEntity.ok(response);
        } else {
            List<BankAccountDTO> bankAccountDTOS = userDaoService.getBankAccountsByUsername(username);
            ServiceResponse<List<BankAccountDTO>> response = new ServiceResponse<>("success", bankAccountDTOS);
            return ResponseEntity.ok(response);
        }
    }
}
