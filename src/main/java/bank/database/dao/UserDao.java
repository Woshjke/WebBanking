package bank.database.dao;

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

    public void createUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public User getUserById(Long id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    public List<User> getUserList() {

        // открыть сессию - для манипуляции с персист. объектами
        Session session = sessionFactory.openSession();

        //session.get(User.class, 1L); // получение объекта по id

        // этап подготовки запроса

        // объект-конструктор запросов для Criteria API
        // не использовать session.createCriteria, т.к. deprecated
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<User> cq = cb.createQuery(User.class);

        // первостепенный, корневой entity (в sql запросе - from)
        Root<User> root = cq.from(User.class);

        // необязательный оператор, если просто нужно получить все значения
        //cq.select(root);

        // этап выполнения запроса
        Query query = session.createQuery(cq);

        List<User> userList = query.getResultList();

        session.close();

        return userList;
    }

    public void updateUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }
}
