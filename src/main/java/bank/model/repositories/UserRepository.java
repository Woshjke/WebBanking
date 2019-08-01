package bank.model.repositories;

import bank.model.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for User table
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);
    User findById(Long id);
    List<User> getAllByStatus(String status);
}
