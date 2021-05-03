package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
//        SessionFactory sessionFactory = Util.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        try {
//            session.beginTransaction();
//            session.createSQLQuery("CREATE TABLE users( id INT NOT NULL AUTO_INCREMENT, name VARCHAR(50) NOT NULL, " +
//                    "lastname VARCHAR(50) NOT NULL, age INT NOT NULL, PRIMARY KEY (id) )").executeUpdate();
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            session.getTransaction().rollback();
//        } finally {
//            session.close();
//        }

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createEntityGraph(User.class);
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {

        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {

        Session session = Util.getSessionFactory().openSession();
        return session.createQuery("From " + User.class.getSimpleName()).list();
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM users").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
