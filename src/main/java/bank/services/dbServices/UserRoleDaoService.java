package bank.services.dbServices;

import bank.model.repositories.UserRoleRepository;
import bank.model.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleDaoService {
    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleDaoService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public List<UserRole> getUserRolesByUserId(long id) {
        return userRoleRepository.getUserRolesByUserId(id);
    }
}
