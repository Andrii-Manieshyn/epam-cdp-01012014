package service;

import model.Event;
import model.Ticket;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author  Andrii_Manieshyn 10.04.2014.
 */
public class TicketService {

    private EntityManagerFactory entityManagerFactory;
    private UserService userService;
    private EventService eventService;

    public Ticket findTicketById(long id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Ticket ticket = entityManager.find(Ticket.class, id);
        entityManager.close();
        return ticket;
    }

    /**
     * Book ticket for a specified event on behalf of specified user.
     * @param userId User Id.
     * @param eventId Event Id.
     * @param place Place number.
     * @param category Service category.
     * @return Booked ticket object.
     * @throws java.lang.IllegalStateException if this place has already been booked.
     */
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
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
        entityManager.getTransaction().begin();
        entityManager.persist(ticket);
        entityManager.getTransaction().commit();
        entityManager.close();
        return ticket;
    }

    /**
     * Get all booked tickets for specified user. Tickets should be sorted by event date in descending order.
     * @param user User
     * @return List of Ticket objects.
     */
    public List<Ticket> getBookedTickets(User user){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Ticket> queryProducts= entityManager.createNamedQuery("Ticket.allTicketsByUser", Ticket.class);
        queryProducts.setParameter("user", user);
        List<Ticket>tickets = queryProducts.getResultList();
        entityManager.close();
        return tickets;
    }

    /**
     * Get all booked tickets for specified event. Tickets should be sorted in by user email in ascending order.
     * @param event Event
     * @return List of Ticket objects.
     */
    public List<Ticket> getBookedTickets(Event event){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Ticket> queryProducts= entityManager.createNamedQuery("Ticket.allTicketsByEvent", Ticket.class);
        queryProducts.setParameter("event", event);
        List<Ticket>tickets = queryProducts.getResultList();
        entityManager.close();
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
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(ticket);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        }

        return false;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
}
