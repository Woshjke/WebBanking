package bank.controllers;

import bank.AuthenticationHelperService;
import bank.RequestValidator;
import bank.model.dto.BankAccountDTO;
import bank.model.dto.OrganisationsDTO;
import bank.model.dto.RoleDTO;
import bank.model.dto.UserDTO;
import bank.model.entity.Role;
import bank.model.entity.User;
import bank.responses.AllUserDataResponse;
import bank.services.AdminAccountService;
import bank.services.dbServices.BankAccountDaoService;
import bank.services.dbServices.RoleDaoService;
import bank.services.dbServices.UserDaoService;
import bank.services.dbServices.UserRoleDaoService;
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
    private RoleDaoService roleDaoService;
    private AuthenticationHelperService authenticationHelperService;

    @Autowired
    public AdminAccountController(UserDaoService userDaoService,
                                  AdminAccountService adminService,
                                  BankAccountDaoService bankAccountDaoService,
                                  UserRoleDaoService userRoleDaoService,
                                  RequestValidator validator,
                                  RoleDaoService roleDaoService,
                                  AuthenticationHelperService authenticationHelperService) {
        this.userDaoService = userDaoService;
        this.adminService = adminService;
        this.bankAccountDaoService = bankAccountDaoService;
        this.userRoleDaoService = userRoleDaoService;
        this.validator = validator;
        this.roleDaoService = roleDaoService;
        this.authenticationHelperService =authenticationHelperService;
    }

    /**
     * Method shows admin page.
     *
     * @return admin page.
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
     * Method processes a request of getting user update page.
     *
     * @return user update page.
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
     * Method processes a user updating request and calls user updating service.
     *
     * @param request - request, that was send form view.
     * @return admin page with result of updating user.
     */
    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    public RedirectView doUpdateUser(HttpServletRequest request) {
        User authUser = authenticationHelperService.getAuthenticatedUser(false);
        try {
            validator.isValidUserUpdateRequest(request);
            Long userToUpdateId = Long.parseLong(request.getParameter("id"));
            String newUsername = request.getParameter("username");
            String newPassword = request.getParameter("password");
            adminService.updateUser(userToUpdateId, newUsername, newPassword);
            if (userToUpdateId.equals(authUser.getId())) {
                return new RedirectView("/process_logout");
            }
            return new RedirectView(ADMIN_PAGE + "?resultMessage=Update completed");
        } catch (Exception ex) {
            return new RedirectView(ADMIN_PAGE + "?resultMessage=" + ex.getMessage());
        }
    }

    /**
     * Method processes a request of getting user delete page.
     *
     * @return user delete page.
     */
    @RequestMapping(value = "/deleteUser", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showDeleteUserPage() {
        ModelAndView mnv = new ModelAndView(DELETE_USER_PAGE);
        User authUser = authenticationHelperService.getAuthenticatedUser();
        List<User> userList = userDaoService.getUsers().stream()
                .filter(x -> !x.getUsername().equals(authUser.getUsername()))
                .collect(Collectors.toList());
        mnv.addObject("usersList", userList);
        return mnv;
    }

    /**
     * Method processes a request to delete a user and calls a service for that.
     *
     * @param request - request, that was send form view.
     * @return admin page with result of deleting user.
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
     * Method processes a request to show a page on which user information can be read.
     *
     * @return user information reading page.
     */
    @RequestMapping(value = "/readUsers", method = RequestMethod.GET)
    public ModelAndView readUsers() {
        return new ModelAndView(READ_USERS_PAGE);
    }

    /**
     * Processes a request to show bank accounts reading page.
     *
     * @return bank accounts reading page.
     */
    @RequestMapping(value = "/readBankAccounts", method = RequestMethod.GET)
    public ModelAndView readBankAccounts() {
        return new ModelAndView(READ_BANK_ACCOUNTS);
    }

    /**
     * Method processes a AJAX call request of finding users information and sending it on users reading page.
     *
     * @param username - username of user, that u want to find with all his information, or empty spring if you want to
     *                 get all users with all the information about them.
     * @return all users DTOs with user bank accounts, organisations, roles (if username was not specified) or specific
     * user DTO with user bank accounts, organisations, roles (if username was specified).
     */
    @RequestMapping(value = "/filterUsers", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllUsers(@RequestParam String username) {
        if (username.equals("")) {
            List<UserDTO> userDTOS = userDaoService.getUserDtoList();
            AllUserDataResponse[] response = new AllUserDataResponse[userDTOS.size()];
            for (int i = 0; i < response.length; i++) {
                UserDTO currentUser = userDTOS.get(i);
                response[i] = new AllUserDataResponse();
                response[i].setUser(currentUser);

                List<BankAccountDTO> bankAccountDTOList =
                        bankAccountDaoService.getUserBankAccountDTOS(currentUser.getId());

                if (bankAccountDTOList != null && bankAccountDTOList.size() > 0) {
                    response[i].setUserBankAccounts(bankAccountDTOList);
                }

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

    /**
     * Method processes a request of getting a page for setting role for user.
     * @return setting role page.
     */
    @RequestMapping(value = "/setRole", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showSetRolePage() {
        ModelAndView mnv = new ModelAndView("set_role_page");
        List<User> userList = userDaoService.getUsers();
        List<Role> roles = roleDaoService.getRoles();
        mnv.addObject("users", userList);
        mnv.addObject("roles", roles);
        return mnv;
    }

    /**
     * Method processes a request to set role for user and calls a service for that.
     * @param request - request, that was send form view with specific role to set.
     * @return admin page with result of setting role for user.
     */
    @RequestMapping(value = "/doSetRole", method = {RequestMethod.POST})
    public RedirectView doSetRole(HttpServletRequest request) {
        try {
            validator.isValidSetRoleRequest(request);
            Long userId = Long.parseLong(request.getParameter("users"));
            String roleName = request.getParameter("role");
            adminService.setRoleForUser(userId, roleName);
            return new RedirectView(ADMIN_PAGE + "?resultMessage=Completed");
        } catch (Exception ex) {
            return new RedirectView(ADMIN_PAGE + "?resultMessage=" + ex.getMessage());
        }
    }
}
