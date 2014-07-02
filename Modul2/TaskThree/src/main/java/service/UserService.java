package service;

import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author  Andrii_Manieshyn 10.04.2014.
 */
public class UserService {

    private EntityManagerFactory entityManagerFactory;

    public User createUser(User user){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
        return user;
    }

    public User findUserById(long id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user =entityManager.find(User.class, id);
        entityManager.close();
        return user;
    }


    public List<User> findUserByName(String name){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<User> queryProducts= entityManager.createNamedQuery("User.findByName", User.class);
        queryProducts.setParameter("name", name);
        List<User>users = queryProducts.getResultList();
        entityManager.close();
        return users;
    }

    public void updateUser (User user){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User userToUpdate = entityManager.find(User.class, user.getId());
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteeUser (User user){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User userToDelete = entityManager.find(User.class, user.getId());
        entityManager.remove(userToDelete);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
}
