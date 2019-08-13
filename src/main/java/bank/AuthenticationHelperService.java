package bank;

import bank.model.entity.Role;
import bank.model.entity.User;
import bank.services.dbServices.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class that provides us to get currently authenticated user and his roles.
 */
@Service
public class AuthenticationHelperService {

    private UserDaoService userDaoService;

    @Autowired
    public AuthenticationHelperService(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    /**
     * This method returns authenticated user object.
     *
     * @param fetchBankAccount - true, if you need to fetch bank accounts to User object, false in other cases.
     * @return authenticated user object.
     */
    public User getAuthenticatedUser(Boolean fetchBankAccount) {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authUser;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        if (username.equals("anonymousUser")) {
            authUser = new User();
            Set<Role> roles = new HashSet<>();
            roles.add(new Role("ROLE_ANONYMOUS"));
            authUser.setUsername("anonymous");
            authUser.setRoles(roles);
        } else {
            if (!fetchBankAccount) {
                authUser = userDaoService.getUserByUsernameWithFetchRoles(username);
            } else {
                authUser = userDaoService.getUserByUsernameWithFetchAll(username);
            }

        }
        return authUser;
    }

    /**
     * This method calls getAuthenticatedUser method with parameter true.
     * See getAuthenticatedUser(Boolean fetchBankAccount).
     *
     * @return authenticated user object.
     */
    public User getAuthenticatedUser() {
        return getAuthenticatedUser(true);
    }

    /**
     * Method returns list of roles of authenticated user.
     *
     * @return list of roles of authenticated user.
     */
    public List<String> getAuthUserRoles() {
        return getAuthenticatedUser(false).getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
