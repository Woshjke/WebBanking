package bank.services.dbServices;

import bank.model.entity.BankAccount;
import bank.model.repositories.TransactionRepository;
import bank.model.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionDaoService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionDaoService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * saving or updating transaction object in database
     * @param transaction - transaction to save/update
     */
    public void createTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    /**
     * Getting list of transaction from database
     * @return list of transaction
     */
    public List<Transaction> readTransactions() {
        return (List<Transaction>) transactionRepository.findAll();
    }

    /**
     * Deleting transaction from database
     * @param transaction - transaction to delete
     */
    public void deleteTransaction(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    public List<Transaction> findBySourceBankAccount(BankAccount sourceBankAccount) {
        return transactionRepository.findBySource(sourceBankAccount);
    }

    public List<Transaction> findByDestinationBankAccount(BankAccount destinationBankAccount) {
        return transactionRepository.findByDestination(destinationBankAccount);
    }

    public List<Transaction> findBySourceOrDestinationBankAccount(BankAccount destinationBankAccount) {
        return transactionRepository.findBySourceOrDestination(destinationBankAccount);
    }
}
