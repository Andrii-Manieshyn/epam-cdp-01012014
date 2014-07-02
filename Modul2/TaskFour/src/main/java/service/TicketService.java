package service;

import model.Event;
import model.Ticket;
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
public class TicketService {

    private SessionFactory sessionFactory;
    private UserService userService;
    private EventService eventService;

    public Ticket findTicketById(long id){
        Session session = sessionFactory.openSession();
        return (Ticket) session.load(Ticket.class, id);
    }

    /**
     * Book ticket for a specified event on behalf of specified user.
     * @param userId User Id.
     * @param eventId Event Id.
     * @param place Place number.
     * @param category Service category.
     * @return Booked ticket object.
     * @throws IllegalStateException if this place has already been booked.
     */
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category){
        User user = userService.findUserById(userId);
        List<Ticket> tickets = getBookedTickets(user);
        for (Ticket ticket : tickets){
            if(ticket.getEvent().getId()== eventId && ticket.getPlace()== place && ticket.getCategory() == category) return ticket;
        }
        Ticket ticket = new Ticket();
        ticket.setPlace(place);
        ticket.setCategory(category);
        ticket.setEvent(eventService.findEventById(eventId));
        ticket.setUser(userService.findUserById(userId));
        Session session = sessionFactory.openSession();;
        session.getTransaction().begin();
        session.persist(ticket);
        session.getTransaction().commit();
        return ticket;
    }

    /**
     * Get all booked tickets for specified user. Tickets should be sorted by event date in descending order.
     * @param user User
     * @return List of Ticket objects.
     */
    public List<Ticket> getBookedTickets(User user){
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Ticket.class);
        if(user!=null){
            criteria.add(Restrictions.eq("user", user));
        }
        List<Ticket>tickets = criteria.list();
        return tickets;
    }

    /**
     * Get all booked tickets for specified event. Tickets should be sorted in by user email in ascending order.
     * @param event Event
     * @return List of Ticket objects.
     */
    public List<Ticket> getBookedTickets(Event event){
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Ticket.class);
        if(event!=null){
            criteria.add(Restrictions.eq("event", event));
        }
        List<Ticket>tickets = criteria.list();
        return tickets;
    }

    /**
     * Cancel ticket with a specified id.
     * @param ticketId Ticket id.
     * @return Flag whether anything has been canceled.
     */
    public boolean cancelTicket(long ticketId){
        User user = new User();
        Ticket ticket = findTicketById(ticketId);
        if (ticket!=null){
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            session.delete(ticket);
            session.getTransaction().commit();
            return true;
        }
        return false;
    }

    public List<Ticket> getBookedTickets(Event event, Ticket.Category category){
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Ticket.class);
        if(event!=null){
            criteria.add(Restrictions.eq("event", event));
        }
        if(category!=null){
            criteria.add(Restrictions.eq("category", category));
        }
        List<Ticket>tickets = criteria.list();
        return tickets;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
