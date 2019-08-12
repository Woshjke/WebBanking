package bank.services.dbServices;

import bank.model.repositories.OrganisationRepository;
import bank.model.entity.Organisations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganisationDaoService {

    private final OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationDaoService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    /**
     * Getting organisations from database
     * @return list of organisations
     */
    public List<Organisations> getOrganisations() {
        return (List<Organisations>) organisationRepository.findAll();
    }

    /**
     * Getting organisation form database by ID
     * @param id - organisation ID
     * @return organisation object
     */
    public Organisations getOrganisationsById(Long id) {
        return organisationRepository.findById(id);
    }

    public void save(Organisations organisation) {
        organisationRepository.save(organisation);
    }
}
