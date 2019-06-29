package bank.services.dbServices;

import bank.database.dao.OrganisationDao;
import bank.database.entity.Organisations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrganisationDaoService {
    private final OrganisationDao organisationDao;

    @Autowired
    public OrganisationDaoService(OrganisationDao organisationDao) {
        this.organisationDao = organisationDao;
    }

    public List<Organisations> getOrgs() {
        return organisationDao.getOrgsList();
    }

    public Organisations getOrgById(Long id) {
        return organisationDao.getOrgById(id);
    }
}
