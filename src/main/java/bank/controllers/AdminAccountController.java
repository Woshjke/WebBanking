package bank.controllers;

import bank.RequestValidator;
import bank.model.dto.BankAccountDTO;
import bank.model.dto.OrganisationsDTO;
import bank.model.dto.RoleDTO;
import bank.model.dto.UserDTO;
import bank.model.entity.BankAccount;
import bank.model.entity.User;
import bank.services.AdminAccountService;
import bank.services.dbServices.BankAccountDaoService;
import bank.services.dbServices.OrganisationDaoService;
import bank.services.dbServices.UserDaoService;
import bank.services.dbServices.UserRoleDaoService;
import bank.responses.AllUserDataResponse;
import bank.responses.ServiceResponse;
import groovy.transform.Undefined;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static bank.ApplicationProperties.*;

/**
 * Admin features controller.
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminAccountController {

    private UserDaoService userDaoService;
    private AdminAccountService adminService;
    private BankAccountDaoService bankAccountDaoService;
    private UserRoleDaoService userRoleDaoService;
    private RequestValidator validator;

    @Autowired
    public AdminAccountController(UserDaoService userDaoService,
                                  AdminAccountService adminService,
                                  BankAccountDaoService bankAccountDaoService,
                                  UserRoleDaoService userRoleDaoService,
                                  RequestValidator validator) {
        this.userDaoService = userDaoService;
        this.adminService = adminService;
        this.bankAccountDaoService = bankAccountDaoService;
        this.userRoleDaoService = userRoleDaoService;
        this.validator = validator;
    }

    /**
     * GET-request of admin page
     *
     * @return admin page view
     */
    @RequestMapping(value = "/admin_page", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showAdminPage(@ModelAttribute("resultMessage") String resultMessage) {
        ModelAndView mnv = new ModelAndView(ADMIN_PAGE);
        if (!resultMessage.equals("")) {
            mnv.addObject("resultMessage", resultMessage);
        }
        return mnv;
    }

    /**
     * Handling request of getting user update page
     *
     * @return user updating view with list of users and selected user (if selected)
     */
    @RequestMapping(value = "/updateUser", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showUpdateUserPage(@ModelAttribute("users") String selectedUserId) {
        ModelAndView mnv = new ModelAndView(UPDATE_USER_PAGE);
        List<User> userList = userDaoService.getUsers();
        if (!selectedUserId.equals("")) {
            User userToUpdate = adminService.getUserToUpdate(Long.valueOf(selectedUserId));
            mnv.addObject("userToUpdate", userToUpdate);
        }
        mnv.addObject("usersList", userList);
        return mnv;
    }

    /**
     * handling user updating request and calls user updating service
     *
     * @param request - request from JSP
     * @return admin view page
     */
    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    public RedirectView doUpdateUser(HttpServletRequest request) {
        try {
            validator.isValidUserUpdateRequest(request);
            Long userToUpdateId = Long.parseLong(request.getParameter("id"));
            String newUsername = request.getParameter("username");
            String newPassword = request.getParameter("password");
            adminService.updateUser(userToUpdateId, newUsername, newPassword);
            return new RedirectView(ADMIN_PAGE + "?resultMessage=Update completed");
        } catch (Exception ex) {
            return new RedirectView(ADMIN_PAGE + "?resultMessage=" + ex.getMessage());
        }
    }

    /**
     * Handling request of getting user delete page
     *
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
     *
     * @param request - request from jsp with selected user param
     * @return admin page view with result of user deletion message
     */
    @RequestMapping(value = "/doDelete", method = RequestMethod.POST)
    public RedirectView doDeleteUser(HttpServletRequest request) {
        try {
            validator.isValidUserDeleteRequest(request);
            Long userId = Long.parseLong(request.getParameter("users"));
            adminService.deleteUser(userId);
            return new RedirectView(ADMIN_PAGE + "?resultMessage=Completed");
        } catch (Exception ex) {
            return new RedirectView(ADMIN_PAGE + "?resultMessage=" + ex.getMessage());
        }
    }

    /**
     * Handling request of  getting users info reading  page
     *
     * @return user info reading view
     */
    @RequestMapping(value = "/readUsers", method = RequestMethod.GET)
    public ModelAndView readUsers() {
        return new ModelAndView(READ_USERS_PAGE);
    }

    /**
     * Handling request of  getting bank accounts info reading  page
     *
     * @return bank accounts info reading view
     */
    @RequestMapping(value = "/readBankAccounts", method = RequestMethod.GET)
    public ModelAndView readBankAccounts() {
        return new ModelAndView(READ_BANK_ACCOUNTS);
    }

    /**
     * Handling AJAX call form users reading view
     *
     * @param username - specific username in
     * @return all users if username field was empty or specific user if username field wasn't empty
     */
    @RequestMapping(value = "/filterUsers", method = RequestMethod.GET)
    //todo Пофиксить ситуацию, когда нет банковского аккаунта
    public ResponseEntity<Object> getAllUsers(@RequestParam String username) {
        if (username.equals("")) {
            List<UserDTO> userDTOS = userDaoService.getUserDtoList();
            AllUserDataResponse[] response = new AllUserDataResponse[userDTOS.size()];
            for (int i = 0; i < response.length; i++) {
                UserDTO currentUser = userDTOS.get(i);
                response[i] = new AllUserDataResponse();
                response[i].setUser(currentUser);
                response[i].setUserBankAccounts(bankAccountDaoService.getUserBankAccountDTOS(currentUser.getId()));
                response[i].setUserRoles(userRoleDaoService.getUserRolesByUserId(currentUser.getId())
                        .stream()
                        .filter(x -> x.getUser().getUsername().equals(currentUser.getUsername()))
                        .map(x -> new RoleDTO(x.getRole().getId(), x.getRole().getName()))
                        .collect(Collectors.toList()));
                response[i].setUserOrganisations(bankAccountDaoService.getBankAccountsByUserId(currentUser.getId())
                        .stream()
                        .map(x -> {
                            if (x.getOrganisation() != null) {
                                return new OrganisationsDTO(x.getOrganisation().getId(), x.getOrganisation().getName());
                            } else return null;
                        })
                        .collect(Collectors.toList()));
                response[i].setStatus("success");
            }
            return ResponseEntity.ok(response);
        } else {
            UserDTO userDTO = userDaoService.getUserDtoByUsername(username);
            AllUserDataResponse[] response = new AllUserDataResponse[1];
            response[0] = new AllUserDataResponse();
            response[0].setUser(userDTO);
            response[0].setUserBankAccounts(bankAccountDaoService.getUserBankAccountDTOS(userDTO.getId()));
            response[0].setUserRoles(userRoleDaoService.getUserRolesByUserId(userDTO.getId())
                    .stream()
                    .filter(x -> x.getUser().getUsername().equals(userDTO.getUsername()))
                    .map(x -> new RoleDTO(x.getRole().getId(), x.getRole().getName()))
                    .collect(Collectors.toList()));
            response[0].setUserOrganisations(bankAccountDaoService.getBankAccountsByUserId(userDTO.getId())
                    .stream()
                    .map(x -> {
                        if (x.getOrganisation() != null) {
                            return new OrganisationsDTO(x.getOrganisation().getId(), x.getOrganisation().getName());
                        } else return null;
                    })
                    .collect(Collectors.toList()));
            response[0].setStatus("success");
            return ResponseEntity.ok(response);
        }
    }


    @RequestMapping(value = "/activateUser", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showActivateUserPage() {
        ModelAndView mnv = new ModelAndView("activate_user_page");
        List<User> usersToActivate = userDaoService.getUserListByStatus("disabled");
        if (usersToActivate.isEmpty()) {
            return new ModelAndView(new RedirectView(ADMIN_PAGE + "?resultMessage=No users to activate"));
        }
        mnv.addObject("usersToActivate", usersToActivate);
        return mnv;
    }

    @RequestMapping(value = "/doActivateUser", method = {RequestMethod.POST, RequestMethod.GET})
    public RedirectView doActivateUser(HttpServletRequest request) {
        try {
            validator.isValidActivateRequest(request);
            Long userId = Long.valueOf(request.getParameter("users"));
            adminService.activateAccount(userId);
            return new RedirectView(ADMIN_PAGE + "?resultMessage=User activation completed");
        } catch (Exception ex) {
            return new RedirectView(ADMIN_PAGE + "?resultMessage=" + ex.getMessage());
        }
    }
}
