package bank.services.dbServices;

import bank.model.entity.Role;
import bank.model.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class that performs calls to Role repository.
 */
@Service
@Transactional
public class RoleDaoService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleDaoService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Getting roles from database.
     *
     * @return list of roles.
     */
    public List<Role> getRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    /**
     * Getting roles from database by ID.
     *
     * @param id - ID of role.
     * @return role object from database.
     */
    public Role getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    /**
     * Getting roles from database by name of role.
     *
     * @param roleName - name of role.
     * @return role object from database.
     */
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }
}
