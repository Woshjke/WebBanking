package bank.model.repositories;

import bank.model.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserDetailsRepository
        extends CrudRepository<UserDetails, Integer>, JpaSpecificationExecutor<UserDetails> {
    UserDetails findById(Long id);
}
