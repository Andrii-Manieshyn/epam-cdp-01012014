package com.cdp.dao;

import com.cdp.entity.Priority;
import com.cdp.entity.ToDoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Andrii_Manieshyn
 */
@Component
public class IToDoItemDaoImpl implements IToDoItemDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public ToDoItem findById(Long itemId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ToDoItem user =entityManager.find(ToDoItem.class, itemId);
        entityManager.close();
        return user;
    }

    @Override
    public Collection<ToDoItem> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<ToDoItem> queryProducts= entityManager.createNamedQuery("ToDoItem.findAll", ToDoItem.class);
        List<ToDoItem> toDoItemList = queryProducts.getResultList();
        entityManager.close();
        return toDoItemList;
    }

    @Override
    public ToDoItem create(ToDoItem item) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(item);
        entityManager.getTransaction().commit();
        entityManager.close();
        return item;
    }

    @Override
    public ToDoItem update(ToDoItem item) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(item);
        entityManager.getTransaction().commit();
        entityManager.close();
        return item;
    }

    @Override
    public boolean remove(ToDoItem item) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(item) ? item : entityManager.merge(item));
        entityManager.getTransaction().commit();
        entityManager.close();
        return true;
    }

    @Override
    public Collection<ToDoItem> findByPriority(Priority priority) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<ToDoItem> queryProducts= entityManager.createNamedQuery("ToDoItem.findByPriority", ToDoItem.class);
        queryProducts.setParameter("priority", priority);
        List<ToDoItem>doItemList = queryProducts.getResultList();
        entityManager.close();
        return doItemList;
    }

    @Override
    public Collection<ToDoItem> findEndingOne(Date endingDate) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<ToDoItem> queryProducts= entityManager.createNamedQuery("ToDoItem.findEndingOne", ToDoItem.class);
        queryProducts.setParameter("endingDate", endingDate);
        List<ToDoItem>doItemList = queryProducts.getResultList();
        entityManager.close();
        return doItemList;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
}
