package bank.services.dbServices;

import bank.model.dao.OrganisationRepository;
import bank.model.entity.Organisations;
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

    /**
     * Getting organisations from database
     * @return list of organisations
     */
    public List<Organisations> getOrganisations() {
        return (List<Organisations>) organisationDao.findAll();
    }

    /**
     * Getting organisation form database by ID
     * @param id - organisation ID
     * @return organisation object
     */
    public Organisations getOrganisationsById(Long id) {
        return organisationDao.findById(id);
    }
}
