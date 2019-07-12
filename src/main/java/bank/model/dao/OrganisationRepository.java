package bank.model.dao;

import bank.model.entity.Organisations;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganisationRepository extends CrudRepository<Organisations, Integer>, JpaSpecificationExecutor<Organisations> {
    List<Organisations> getAllById(Long id);
    Organisations findById(Long id);
}
