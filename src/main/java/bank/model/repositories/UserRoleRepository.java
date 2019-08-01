package bank.model.repositories;

import bank.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Integer>, JpaSpecificationExecutor<UserRole> {
    List<UserRole> getUserRolesByUserId(long id);
}
