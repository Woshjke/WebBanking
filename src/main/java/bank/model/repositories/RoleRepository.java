package bank.model.repositories;

import bank.model.entity.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for Role table
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
    List<Role> getAllById(Long id);
    Role findById(Long id);
}
