package bank.database.dao;

import bank.database.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

// TODO: 27.06.2019 put на update delete на delete (http запросы)
// TODO: 27.06.2019 Чекнуть пакет nio
// TODO: 27.06.2019 паттерн Dispatcher полазить по классу DispatcherServlet


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
        session.beginTransaction();

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

    public User getUserByUsername(String username) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root).where(cb.equal(root.get("username"), username));
        Query query = session.createQuery(cq);
        User user = (User) query.getSingleResult();
        session.close();
        return user;
    }
}
