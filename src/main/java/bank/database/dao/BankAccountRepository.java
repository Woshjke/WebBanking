package bank.database.dao;

import bank.database.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Integer>, JpaSpecificationExecutor<BankAccount> {
    List<BankAccount> getAllByUser_Id(Long id);
    BankAccount findById(Long id);
}
