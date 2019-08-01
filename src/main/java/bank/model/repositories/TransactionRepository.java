package bank.model.repositories;

import bank.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for Transaction table
 */
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer>, JpaSpecificationExecutor<Transaction> {
    Transaction findById(Long id);
}
