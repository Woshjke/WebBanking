package bank.services.dbServices;

import bank.model.dao.RoleRepository;
import bank.model.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleDaoService {

    private RoleRepository roleDao;

    @Autowired
    public RoleDaoService(RoleRepository roleDao) {
        this.roleDao = roleDao;
    }

    public List<Role> getRoles() {
        return (List<Role>) roleDao.findAll();
    }
}
