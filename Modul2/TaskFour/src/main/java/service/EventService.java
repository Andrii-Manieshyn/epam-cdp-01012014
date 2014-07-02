package service;

import model.Event;
import model.Ticket;
import org.hibernate.*;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.List;

/**
 * @author  Andrii_Manieshyn 10.04.2014.
 */
public class EventService {

    private SessionFactory sessionFactory;

    public Event createEvent(Event event){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(event);
        session.getTransaction().commit();
        session.close();
        return event;
    }

    public Event findEventById(long id){
        Session session = sessionFactory.openSession();
        Event event= (Event) session.load(Event.class, id);
        event.getTicketList();
        session.clear();
        return event;
    }


    public List<Event> findEventByTitle(String title){
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Event.class);
        if(title!=null){
            criteria.add(Restrictions.eq("title", title));
        }
        List<Event>events = criteria.list();
        session.close();
        return events;
    }

    public List<Event> findEventByDate(Date date){
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Event.class);
        if(date!=null){
            criteria.add(Restrictions.eq("date", date));
        }
        List<Event>events = criteria.list();
        session.close();
        return events;
    }

    public void updateEvent (Event event){
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Event eventToUpdate = (Event)session.get(Event.class, event.getId());
        eventToUpdate.setDate(event.getDate());
        eventToUpdate.setTitle(event.getTitle());
        eventToUpdate.setTicketList(event.getTicketList());
        session.update(eventToUpdate);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteeEvent (Event event){
        Session session = sessionFactory.openSession();
        Event eventToDelete = (Event)session.load(Event.class, event.getId());
        session.getTransaction().begin();
        session.delete(eventToDelete);
        session.getTransaction().commit();
        session.close();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
