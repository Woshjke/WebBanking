package bank.database.dao;

import bank.database.entity.BankAccount;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class BankAccountDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public BankAccountDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createBankAccount(BankAccount bankAccount) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(bankAccount);
        session.getTransaction().commit();
        session.close();
    }

    public BankAccount getBankAccountById(Long id) {
        Session session = sessionFactory.openSession();
        BankAccount bankAccount = session.get(BankAccount.class, id);
        session.close();
        return bankAccount;
    }

    public List<BankAccount> getAllBankAccounts() {

        // открыть сессию - для манипуляции с персист. объектами
        Session session = sessionFactory.openSession();

        //session.get(User.class, 1L); // получение объекта по id

        // этап подготовки запроса

        // объект-конструктор запросов для Criteria API
        // не использовать session.createCriteria, т.к. deprecated
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<BankAccount> cq = cb.createQuery(BankAccount.class);

        // первостепенный, корневой entity (в sql запросе - from)
        Root<BankAccount> root = cq.from(BankAccount.class);

        // необязательный оператор, если просто нужно получить все значения
        //cq.select(root);

        // этап выполнения запроса
        Query query = session.createQuery(cq);

        List<BankAccount> bankAccountList = query.getResultList();

        session.close();

        return bankAccountList;
    }

    public void updateBankAccount(BankAccount bankAccount) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(bankAccount);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteBankAccount(BankAccount bankAccount) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(bankAccount);
        session.getTransaction().commit();
        session.close();
    }
}
