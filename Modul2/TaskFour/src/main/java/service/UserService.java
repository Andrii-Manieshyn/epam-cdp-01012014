package service;

import model.Event;
import model.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author  Andrii_Manieshyn 10.04.2014.
 */
public class UserService {

    private SessionFactory sessionFactory;

    public User createUser(User user){
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.persist(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public User findUserById(long id){
        Session session = sessionFactory.openSession();
        User user = (User) session.load(User.class, id);
        user.getTicketList();
        session.close();
        return user;
    }


    public List<User> findUserByName(String name){
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(User.class);
        if(name!=null){
            criteria.add(Restrictions.eq("name", name));
        }
        List<User>users = criteria.list();
        for (User iter : users){
            iter.getTicketList();
        }
        session.close();
        return users;
    }

    public void updateUser (User user){
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        User userToUpdate = (User) session.load(User.class, user.getId());
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        session.getTransaction().commit();
        session.close();
    }

    public void deleteUser(User user){
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        User userToDelete = (User) session.get(User.class, user.getId());
        session.delete(userToDelete);
        session.getTransaction().commit();
        session.close();
    }


    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
