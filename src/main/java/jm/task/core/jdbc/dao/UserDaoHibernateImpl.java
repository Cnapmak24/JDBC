package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.UtilHibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.query.Query;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private String sql;
    private Transaction tr = null;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try(Session session = UtilHibernate.getSessionFactory().openSession()){
            tr = session.beginTransaction();
            sql = "CREATE TABLE IF NOT EXISTS User(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45),  lastName VARCHAR(45), age TINYINT(3))";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            tr.commit();
        } catch (Exception e){
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = UtilHibernate.getSessionFactory().openSession()){
            tr = session.beginTransaction();
            sql = "DROP TABLE IF EXISTS User";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            tr.commit();
        } catch (Exception e){
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = UtilHibernate.getSessionFactory().openSession()){
            tr = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            tr.commit();
        }catch (Exception e){
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = UtilHibernate.getSessionFactory().openSession()){
            tr = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            tr.commit();
        }catch (Exception e){
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = UtilHibernate.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = UtilHibernate.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            sql = "delete from USER";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            tr.commit();
        } catch(Exception e){
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }
    }
}
