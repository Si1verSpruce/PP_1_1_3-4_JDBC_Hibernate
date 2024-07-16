package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.List;

import static org.hibernate.resource.transaction.spi.TransactionStatus.*;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            executeTransaction(session.getTransaction(),
                    () -> session.createNativeQuery(UserDaoJDBCImpl.CREATE_TABLE_SQL, User.class).executeUpdate());
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            executeTransaction(session.getTransaction(),
                    () -> session.createNativeQuery(UserDaoJDBCImpl.DROP_TABLE_SQL, User.class).executeUpdate());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            executeTransaction(session.getTransaction(), () -> session.persist(new User(name, lastName, age)));
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            executeTransaction(session.getTransaction(), () -> session.remove(session.get(User.class, id)));
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            executeTransaction(session.getTransaction(),
                    () -> session.createMutationQuery("delete from User").executeUpdate());
        }
    }

    private void executeTransaction(Transaction transaction, Runnable runnable) {
        try {
            transaction.begin();
            runnable.run();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.getStatus() == ACTIVE || transaction.getStatus() == MARKED_ROLLBACK) {
                transaction.rollback();
            }
        }
    }
}
