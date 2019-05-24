package bank.database;

import bank.database.entity.User;
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
public class UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    List getUserList() {

        // открыть сессию - для манипуляции с персист. объектами
        Session session = sessionFactory.openSession();

//        session.get(User.class, 1L); // получение объекта по id


        // этап подготовки запроса

        // объект-конструктор запросов для Criteria API
        CriteriaBuilder cb = session.getCriteriaBuilder();// не использовать session.createCriteria, т.к. deprecated

        CriteriaQuery cq = cb.createQuery(User.class);

        Root root = cq.from(User.class);// первостепенный, корневой entity (в sql запросе - from)

        cq.select(root);// необязательный оператор, если просто нужно получить все значения


        // этап выполнения запроса
        Query query = session.createQuery(cq);

        List userList = query.getResultList();

        session.close();

        return userList;
    }

    void addUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public User getUser(String login) {
        return null;
    }
}
