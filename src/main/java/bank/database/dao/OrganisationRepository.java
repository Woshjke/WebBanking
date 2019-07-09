package bank.database.dao;

import bank.database.entity.Organisations;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends CrudRepository<Organisations, Integer>, JpaSpecificationExecutor<Organisations> {
}
