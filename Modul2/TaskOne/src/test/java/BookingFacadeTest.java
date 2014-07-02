import dao.EventDao;
import facade.BookingFacade;
import facade.FacadeUtils;
import junit.framework.Assert;
import model.Event;
import model.Ticket;
import model.User;
import model.impl.EventImpl;
import model.impl.TicketImpl;
import model.impl.UserImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Andrii_Manieshyn 10.04.2014.
 */
public class BookingFacadeTest {
    BookingFacade bookingFacade;
    @Before
    public void init(){
        bookingFacade = FacadeUtils.getBookingFacade();
    }

    @Test
    public void whenGetEventById_thenReturnEventWithSettedId(){
        Event event = bookingFacade.getEventById(1);
        Assert.assertEquals(event.getId(), 1);
        Assert.assertEquals(event.getTitle(), "Home Alone");
        Assert.assertEquals(event.getDate().getTime() / 1_000_000, 1387836);
    }

    @Test
    public void whenGetEventsByTitle_thenReturnEventWithSettedTitle(){
        List<Event> events = bookingFacade.getEventsByTitle("Home Alone", 1, 1);
        Assert.assertEquals(events.get(0).getId(), 1);
        Assert.assertEquals(events.get(0).getTitle(), "Home Alone");
        Assert.assertEquals(events.get(0).getDate().getTime() / 1_000_000, 1387836);
    }

    @Test
    public void whenGetEventsForDay_thenReturnEventWithSettedDay(){
        Event event = bookingFacade.getEventById(1);

        List<Event> events = bookingFacade.getEventsForDay(event.getDate(), 1, 1);
        Assert.assertEquals(events.get(0).getId(), 1);
        Assert.assertEquals(events.get(0).getTitle(), "Home Alone");
        Assert.assertEquals(events.get(0).getDate().getTime() / 1_000_000, 1387836);
    }

    @Test
    public void whenCreateEvent_thenEventCreated(){
        Event event = new EventImpl();
        event.setDate(new Date(System.currentTimeMillis()));
        event.setTitle("Party hard");
        Event createdEvent = bookingFacade.createEvent(event);
        Assert.assertTrue(createdEvent.getId() != 0);
        Event getEvent = bookingFacade.getEventById(createdEvent.getId());
        Assert.assertEquals(createdEvent.getId(), getEvent.getId());


        bookingFacade.deleteEvent(createdEvent.getId());
    }

    @Test
    public void whenDeleteEvent_thenEventDeleted(){
        Event event = new EventImpl();
        event.setDate(new Date(System.currentTimeMillis()));
        event.setTitle("Party hard");
        Event createdEvent = bookingFacade.createEvent(event);
        Assert.assertTrue(createdEvent.getId()!=0);
        Event getEvent = bookingFacade.getEventById(createdEvent.getId());
        Assert.assertEquals(createdEvent.getId(), getEvent.getId());


        boolean deleted =  bookingFacade.deleteEvent(createdEvent.getId());
        Assert.assertTrue(deleted);

        deleted =  bookingFacade.deleteEvent(createdEvent.getId());
        Assert.assertFalse(deleted);

        getEvent = bookingFacade.getEventById(createdEvent.getId());
        Assert.assertNull(getEvent);
    }


    @Test
    public void whenUpdateEvent_thenEventUpdated(){
        Event event = bookingFacade.getEventById(1);
        Assert.assertEquals(event.getId(), 1);
        Assert.assertEquals(event.getTitle(), "Home Alone");
        Assert.assertEquals(event.getDate().getTime() / 1_000_000, 1387836);

        event.setTitle("Party very hard");
        bookingFacade.updateEvent(event);

        event = bookingFacade.getEventById(1);
        Assert.assertEquals(event.getId(), 1);
        Assert.assertEquals(event.getTitle(), "Party very hard");
        Assert.assertEquals(event.getDate().getTime() / 1_000_000, 1387836);

        event.setTitle( "Home Alone");
        bookingFacade.updateEvent(event);
    }



    @Test
    public void whenGetUsertById_thenReturnUserWithSettedId(){
        User user = bookingFacade.getUserById(1);
        Assert.assertEquals(user.getId(), 1);
        Assert.assertEquals(user.getName(), "John Smith");
        Assert.assertEquals(user.getEmail(), "smith@gmail.com");
    }

    @Test
    public void whenGetUserByName_thenReturnUserWithSettedName(){
        User user = bookingFacade.getUsersByName("John Smith", 1, 1).get(0);
        Assert.assertEquals(user.getId(), 1);
        Assert.assertEquals(user.getName(), "John Smith");
        Assert.assertEquals(user.getEmail(), "smith@gmail.com");
    }

    @Test
    public void whenGetUserByEmail_thenReturnUserWithSettedEmail(){
        User user = bookingFacade.getUserByEmail("smith@gmail.com");
        Assert.assertEquals(user.getId(), 1);
        Assert.assertEquals(user.getName(), "John Smith");
        Assert.assertEquals(user.getEmail(), "smith@gmail.com");
    }


    @Test
    public void whenUpdateUserByEmail_thenUpdateUser(){
        User user = bookingFacade.getUserById(1);
        user.setName("Andrew");
        bookingFacade.updateUser(user);

        user = bookingFacade.getUserById(1);

        Assert.assertEquals(user.getId(), 1);
        Assert.assertEquals(user.getName(), "Andrew");
        Assert.assertEquals(user.getEmail(), "smith@gmail.com");

        user.setName("John Smith");
        bookingFacade.updateUser(user);
    }

    @Test
    public void whenCreateUserByEmail_thenCreateUser(){
        User user = new UserImpl();
        user.setName("Pavel");
        user.setEmail("pavel@mail.com");
        bookingFacade.createUser(user);

        user = bookingFacade.getUserByEmail("pavel@mail.com");

        Assert.assertEquals(user.getName(), "Pavel");
        Assert.assertEquals(user.getEmail(), "pavel@mail.com");

        bookingFacade.deleteUser(user.getId());
    }

    @Test
    public void whenRemoveUserByEmail_thenRemoveUser(){
        User user = new UserImpl();
        user.setName("Pavel");
        user.setEmail("pavel@mail.com");
        bookingFacade.createUser(user);

        user = bookingFacade.getUserByEmail("pavel@mail.com");

        Assert.assertEquals(user.getName(), "Pavel");
        Assert.assertEquals(user.getEmail(), "pavel@mail.com");

        bookingFacade.deleteUser(user.getId());

        user = bookingFacade.getUserByEmail("pavel@mail.com");
        Assert.assertNull(user);

    }


    @Test
    public void whenBookTicket_thenTicketCretedInDatabase(){
        Ticket ticket = new TicketImpl();
        ticket.setCategory(Ticket.Category.PREMIUM);
        ticket.setEventId(1);
        ticket.setPlace(12);
        ticket.setUserId(1);
        ticket = bookingFacade.bookTicket(ticket.getUserId(), ticket.getEventId(), ticket.getPlace(), ticket.getCategory());

        User user = bookingFacade.getUserById(1);
        List<Ticket> tickets = bookingFacade.getBookedTickets(user,1,1);
        Assert.assertNotNull(tickets);
        Assert.assertEquals(tickets.size(),1);
        Assert.assertEquals(tickets.get(0).getCategory(), Ticket.Category.PREMIUM);
        Assert.assertEquals(tickets.get(0).getEventId(), 1);
        Assert.assertEquals(tickets.get(0).getPlace(), 12);

        bookingFacade.cancelTicket(ticket.getId());
    }

    @Test
    public void whenGetBookTicket_thenReturnGetBookTicket(){
        User user = bookingFacade.getUserById(1);
        List<Ticket> tickets = bookingFacade.getBookedTickets(user,1,1);
        Assert.assertNotNull(tickets);
        Assert.assertEquals(tickets.size(),1);
        Assert.assertEquals(tickets.get(0).getCategory(), Ticket.Category.PREMIUM);
        Assert.assertEquals(tickets.get(0).getEventId(), 1);
        Assert.assertEquals(tickets.get(0).getPlace(), 12);
    }



    @Test
    public void whenCancelBookTicket_thenCancelBookTicket(){
        User user = bookingFacade.getUserById(1);
        List<Ticket> tickets = bookingFacade.getBookedTickets(user,10,1);
        Assert.assertNotNull(tickets);
        Assert.assertEquals(tickets.size(),4);

        boolean deleted = bookingFacade.cancelTicket(tickets.get(0).getId());
        Assert.assertTrue(deleted);

        tickets = bookingFacade.getBookedTickets(user,10,1);
        Assert.assertNotNull(tickets);
        Assert.assertEquals(tickets.size(),3);
    }
}
