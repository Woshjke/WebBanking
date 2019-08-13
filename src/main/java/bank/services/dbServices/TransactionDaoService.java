package bank.services.dbServices;

import bank.model.entity.BankAccount;
import bank.model.entity.Transaction;
import bank.model.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class that performs calls to Transactions repository.
 */
@Service
@Transactional
public class TransactionDaoService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionDaoService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * saving or updating transaction object in database
     *
     * @param transaction - transaction to save/update
     */
    public void createTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    /**
     * Getting list of transaction from database
     *
     * @return list of transaction
     */
    public List<Transaction> readTransactions() {
        return (List<Transaction>) transactionRepository.findAll();
    }

    /**
     * Deleting transaction from database
     *
     * @param transaction - transaction to delete
     */
    public void deleteTransaction(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    /**
     * Getting list of transaction from database by source bank account.
     * @param sourceBankAccount - sourceBankAccount.
     * @return list of transaction with specific source bank account.
     */
    public List<Transaction> findBySourceBankAccount(BankAccount sourceBankAccount) {
        return transactionRepository.findBySource(sourceBankAccount);
    }

    /**
     * Getting list of transaction from database by destination bank account.
     * @param destinationBankAccount - destinationBankAccount.
     * @return list of transaction with specific destination bank account.
     */
    public List<Transaction> findByDestinationBankAccount(BankAccount destinationBankAccount) {
        return transactionRepository.findByDestination(destinationBankAccount);
    }
}
