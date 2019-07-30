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

@Service
public class AuthenticationHelper {

    private UserDaoService userDaoService;

    @Autowired
    public AuthenticationHelper(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    /**
     * This method returns authenticated user object
     *
     * @return authenticated user object form database
     */
    public User getAuthenticatedUser() {
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
            authUser = userDaoService.getUserByUsername(username);
        }
        return authUser;
    }

    public List<String> getAuthUserRoles() {
        return getAuthenticatedUser().getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
