package bank.services.dbServices;

import bank.model.dao.TransactionRepository;
import bank.model.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionDaoService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionDaoService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public void createTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public List<Transaction> readTransactions() {
        return (List<Transaction>) transactionRepository.findAll();
    }

    public void deleteTransaction(Transaction transaction) {
        transactionRepository.delete(transaction);
    }
}
