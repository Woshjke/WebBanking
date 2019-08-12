package bank.model.repositories;

import bank.model.entity.BankAccount;
import bank.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for Transaction table
 */
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer>, JpaSpecificationExecutor<Transaction> {
    Transaction findById(Long id);
    List<Transaction> findByDestination(BankAccount destinationBankAccount);
    List<Transaction> findBySource(BankAccount sourceBankAccount);

    @Query("SELECT p FROM Transaction p  WHERE p.source = (:bankAccount) or p.destination = (:bankAccount)")
    List<Transaction> findBySourceOrDestination(@Param("bankAccount") BankAccount bankAccount);
}
