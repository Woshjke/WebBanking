package bank.database.dao;

import bank.database.entity.Role;
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
public class RoleDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public RoleDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Role> getRoles() {
        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Role> cq = cb.createQuery(Role.class);

        Root<Role> root = cq.from(Role.class);

        Query query = session.createQuery(cq);

        List<Role> roleList = query.getResultList();

        session.close();

        return roleList;
    }
}
