package bank.services.dbServices;

import bank.model.entity.UserRole;
import bank.model.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserRoleDaoService {
    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleDaoService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public void save(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

    public List<UserRole> getUserRolesByUserId(long id) {
        return userRoleRepository.getUserRolesByUserId(id);
    }
}
