package bank.database.service;

import bank.database.dao.TransactionDao;
import bank.database.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    private final TransactionDao transactionDao;

    @Autowired
    public TransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Transactional
    public void createTransaction(Transaction transaction) {
        transactionDao.createTransaction(transaction);
    }
}
