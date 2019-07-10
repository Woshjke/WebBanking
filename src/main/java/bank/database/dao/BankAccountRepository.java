package bank.database.dao;

import bank.database.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Integer>, JpaSpecificationExecutor<BankAccount> {
    //todo заменить на что-то типа BankAccount findBankAccountByUserId
    List<BankAccount> getAllByUserId(Long id);
    BankAccount findById(Long id);
}
