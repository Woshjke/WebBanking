package bank.model.repositories;

import bank.model.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for User table
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer>, JpaSpecificationExecutor<User> {
    @Query("SELECT p FROM User p JOIN FETCH p.roles JOIN FETCH p.bankAccounts WHERE p.id = (:id)")
    User findByIdAndFetchAll(@Param("id") Long id);

    @Query("SELECT p FROM User p JOIN FETCH p.roles JOIN FETCH p.bankAccounts WHERE p.username = (:username)")
    User findByUsernameAndFetchAll(@Param("username") String username);

    @Query("SELECT p FROM User p JOIN FETCH p.roles WHERE p.username = (:username)")
    User findByUsernameAndFetchRoles(@Param("username") String username);

    @Query("SELECT p FROM User p JOIN FETCH p.bankAccounts WHERE p.username = (:username)")
    User findByUsernameAndFetchBankAccounts(@Param("username") String username);

    User findByUsername(String username);

    User findById(Long id);

    List<User> getAllByStatus(String status);

    User findByActivationCode(String activationCode);
}
