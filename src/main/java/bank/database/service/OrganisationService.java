package bank.database.service;

import bank.database.dao.OrganisationDao;
import bank.database.entity.Organisations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganisationService {
    private final OrganisationDao organisationDao;

    @Autowired
    public OrganisationService(OrganisationDao organisationDao) {
        this.organisationDao = organisationDao;
    }

    public List<Organisations> getOrgs() {
        return organisationDao.getOrgsList();
    }
}
