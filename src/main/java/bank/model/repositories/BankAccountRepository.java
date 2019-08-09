package bank.model.repositories;

import bank.model.entity.BankAccount;
import bank.model.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for BankAccount table
 */
@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Integer>, JpaSpecificationExecutor<BankAccount> {
    List<BankAccount> getAllByUserId(Long id);
    BankAccount findById(Long id);
    List<BankAccount> findByUser(User user);
    List<BankAccount> findByUserId(long id);
    BankAccount findByCardNumber(String cardNumber);
}
