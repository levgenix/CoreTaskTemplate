package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE " + Util.getTableName("User") +
                    " (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age INT)").executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
//            e.printStackTrace();
            if (null != transaction) {
                try {
                    transaction.rollback();
                } catch (IllegalStateException ex) {
//                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE " + Util.getTableName("User")).executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
//            e.printStackTrace();
            if (null != transaction) {
                try {
                    transaction.rollback();
                } catch (IllegalStateException ex) {
//                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, (byte) age));
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (null != transaction) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (HibernateException | NoResultException e) {
            e.printStackTrace();
            if (null != transaction) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("from User").list();
        } catch (HibernateException | NoResultException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE " + Util.getTableName("User")).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (null != transaction) {
                transaction.rollback();
            }
        }
    }
}
