package bank.services.dbServices;

import bank.database.dao.BankAccountRepository;
import bank.database.entity.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BankAccountService {

    private final BankAccountRepository bankAccountDao;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountDao) {
        this.bankAccountDao = bankAccountDao;
    }


    public void createBankAccount(BankAccount bankAccount) {
        bankAccountDao.save(bankAccount);
    }

    public List<BankAccount> getAllBankAccounts() {
        return (List<BankAccount>) bankAccountDao.findAll();
    }

    public BankAccount getBankAccountById(Long id) {
        return bankAccountDao.findById(id);
    }

    public void updateBankAccount(BankAccount bankAccount) {
        bankAccountDao.save(bankAccount);
    }

    public void deleteBankAccount(BankAccount bankAccount) {
        bankAccountDao.delete(bankAccount);
    }

    public List<BankAccount> getBankAccountByUserId(long id) {
        return bankAccountDao.getAllByUserId(id);
    }
}
