package bank.model.dao;

import bank.model.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Integer>, JpaSpecificationExecutor<BankAccount> {
    List<BankAccount> getAllByUserId(Long id);
    BankAccount findById(Long id);
}
