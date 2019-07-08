package bank.database.dao;

import bank.database.entity.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TransactionDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public TransactionDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createTransaction(Transaction transaction) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(transaction);
        session.getTransaction().commit();
        session.close();
    }

}
