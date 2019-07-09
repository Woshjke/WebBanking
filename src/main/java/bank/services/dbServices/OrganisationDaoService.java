package bank.services.dbServices;

import bank.database.dao.OrganisationRepository;
import bank.database.entity.Organisations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrganisationDaoService {
    private final OrganisationRepository organisationDao;

    @Autowired
    public OrganisationDaoService(OrganisationRepository organisationDao) {
        this.organisationDao = organisationDao;
    }

    public List<Organisations> getOrgs() {
        return (List<Organisations>) organisationDao.findAll();
    }

    public Organisations getOrgById(Long id) {
        return organisationDao.findById(id);
    }
}
