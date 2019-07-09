package bank.database.dao;

import bank.database.entity.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
    List<Role> findById(long id);
}
