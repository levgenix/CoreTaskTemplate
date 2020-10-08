package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session = null;
    {
        try {
            session = Util.getSession(); //session.close();
        } catch (HibernateException throwables) {
            throwables.printStackTrace();
        }
    }
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
// NativeQuery
    }

    @Override
    public void dropUsersTable() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = session.beginTransaction();
        try {
            session.persist(new User(name, lastName, (byte) age)); //session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) {
                tx.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = session.beginTransaction();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> myObject = criteriaQuery.from(User.class);
            Predicate equalId = builder.equal( myObject.get("id"), id);
            criteriaQuery.select(myObject).where(equalId);
            TypedQuery<User> query = session.createQuery(criteriaQuery);
            session.delete(query.getSingleResult());
            tx.commit();
        } catch (HibernateException | NoResultException e) {
            e.printStackTrace();
            if (tx!=null) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        session.beginTransaction();
        List<User> users = session.createQuery("from User").list();
        session.getTransaction().commit();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        session.beginTransaction();
//        try {
//            //System.out.println(session.getEntityManagerFactory().getMetamodel().entity(User.class).getClass().getAnnotation(User.class).getName());
//            for (Annotation a : User.class.getAnnotations()) {
//                System.out.println(a);
//            }
//            //isAnnotationPresent
//            //getAnnotationsByType
//            System.out.println(User.class.getAnnotation(javax.persistence.Table.class));
//        } catch (Exception e) {
//
//        }

        session.createNativeQuery("TRUNCATE TABLE user");
        session.getTransaction().commit();
    }
}
