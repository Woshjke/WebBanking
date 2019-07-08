package bank.services.dbServices;

import bank.database.dao.RoleDao;
import bank.database.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleDaoService {

    private RoleDao roleDao;

    @Autowired
    public RoleDaoService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List<Role> getRoles() {
        return roleDao.getRoles();
    }
}
