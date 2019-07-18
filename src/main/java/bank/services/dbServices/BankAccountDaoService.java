package bank.services.dbServices;

import bank.model.dao.BankAccountRepository;
import bank.model.entity.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BankAccountDaoService {

    private final BankAccountRepository bankAccountDao;

    @Autowired
    public BankAccountDaoService(BankAccountRepository bankAccountDao) {
        this.bankAccountDao = bankAccountDao;
    }

    /**
     * Saving/updating bank account to database
     * @param bankAccount - bank account to save/update
     */
    public void saveBankAccount(BankAccount bankAccount) {
        bankAccountDao.save(bankAccount);
    }

    /**
     * Getting bank accounts for database
     * @return list of bank accounts
     */
    public List<BankAccount> getAllBankAccounts() {
        return (List<BankAccount>) bankAccountDao.findAll();
    }

    /**
     * Getting bank account form database by ID
     * @param id - bank account ID
     * @return bank account object
     */
    public BankAccount getBankAccountById(Long id) {
        return bankAccountDao.findById(id);
    }

    /**
     * Getting bank accounts of specific user
     * @param id - user id
     * @return list of user bank accounts
     */
    public List<BankAccount> getBankAccountsByUserId(long id) {
        return bankAccountDao.getAllByUserId(id);
    }

    /**
     * Updating bank account in database
     * @param bankAccount - bank account to update
     */
    public void updateBankAccount(BankAccount bankAccount) {
        //todo need to be deleted
        bankAccountDao.save(bankAccount);
    }

    /**
     * Deleting bank account from database
     * @param bankAccount - bank account to delete
     */
    public void deleteBankAccount(BankAccount bankAccount) {
        bankAccountDao.delete(bankAccount);
    }
}
