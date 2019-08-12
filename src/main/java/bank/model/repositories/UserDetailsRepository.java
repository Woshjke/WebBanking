package bank.model.repositories;

import bank.model.entity.User;
import bank.model.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository
        extends CrudRepository<UserDetails, Integer>, JpaSpecificationExecutor<UserDetails> {
    UserDetails findById(Long id);
    UserDetails findByUserId(Long id);
    UserDetails findByUser(User user);
}
