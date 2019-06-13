package bank.database.dao;

import bank.database.entity.Organisations;
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
public class OrganisationDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public OrganisationDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Organisations> getOrgsList() {

        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Organisations> cq = cb.createQuery(Organisations.class);
        Root<Organisations> root = cq.from(Organisations.class);

        Query query = session.createQuery(cq);
        List<Organisations> orgList = query.getResultList();
        session.close();

        return orgList;
    }

    public Organisations getOrgById(Long id) {
        Session session = sessionFactory.openSession();
        Organisations organisation = session.get(Organisations.class, id);
        session.close();
        return organisation;
    }

}
