package bank.services.dbServices;

import bank.database.dao.BankAccountDao;
import bank.database.dao.UserDao;
import bank.database.entity.BankAccount;
import bank.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BankAccountService {

    private final BankAccountDao bankAccountDao;

    @Autowired
    public BankAccountService(BankAccountDao bankAccountDao) {
        this.bankAccountDao = bankAccountDao;
    }


    public void createBankAccount(BankAccount bankAccount) {
        bankAccountDao.createBankAccount(bankAccount);
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountDao.getAllBankAccounts();
    }

    public BankAccount getBankAccountById(Long id) {
        return bankAccountDao.getBankAccountById(id);
    }

    public void updateBankAccount(BankAccount bankAccount) {
        bankAccountDao.updateBankAccount(bankAccount);
    }

    public void deleteBankAccount(BankAccount bankAccount) {
        bankAccountDao.deleteBankAccount(bankAccount);
    }
}
