package service;

import model.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * @author  Andrii_Manieshyn 10.04.2014.
 */
public class EventService {

    private EntityManagerFactory entityManagerFactory;

    public Event createEvent(Event event){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(event);
        entityManager.getTransaction().commit();
        entityManager.close();
        return event;
    }

    public Event findEventById(long id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Event event =  entityManager.find(Event.class, id);
        entityManager.close();
        return event;
    }


    public List<Event> findEventByTitle(String title){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Event> queryProducts= entityManager.createNamedQuery("Event.findByTitle", Event.class);
        queryProducts.setParameter("title", title);
        List<Event>events = queryProducts.getResultList();
        entityManager.close();
        return events;
    }

    public List<Event> findEventByDate(Date date){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Event> queryProducts= entityManager.createNamedQuery("Event.findByDate", Event.class);
        queryProducts.setParameter("date", date);
        List<Event>events = queryProducts.getResultList();
        entityManager.close();
        return events;
    }

    public void updateEvent (Event event){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Event eventToUpdate = entityManager.find(Event.class, event.getId());
        eventToUpdate.setTitle(event.getTitle());
        eventToUpdate.setDate(event.getDate());
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteeEvent (Event event){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Event eventToDelete = entityManager.find(Event.class, event.getId());
        entityManager.remove(eventToDelete);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
}
