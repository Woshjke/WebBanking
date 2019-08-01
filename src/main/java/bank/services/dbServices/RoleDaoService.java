package bank.services.dbServices;

import bank.model.repositories.RoleRepository;
import bank.model.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleDaoService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleDaoService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Getting roles from database
     * @return list of roles
     */
    public List<Role> getRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    //todo add crud
}
