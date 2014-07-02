import model.Event;
import model.Ticket;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.EventService;
import service.TicketService;
import service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Andrii_Manieshyn 10.04.2014.
 */
public class TicketDaoTest {

    TicketService bookingFacade;
    UserService userFacade;

    @Before
    public void init() throws SQLException, IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        bookingFacade = (TicketService) context.getBean("ticketService");
        userFacade = (UserService) context.getBean("userService");
    }

    @Test
    public void whenBookTicket_thenTicketCretedInDatabase(){
        Ticket ticket = new Ticket();
        ticket.setCategory(Ticket.Category.PREMIUM);
        Event event = new Event();
        event.setId(1);

        User user = userFacade.findUserById(1);

        ticket.setEvent(event);
        ticket.setPlace(12);
        ticket.setUser(user);
        ticket = bookingFacade.bookTicket(user.getId(), event.getId(), 12, Ticket.Category.PREMIUM);

        User foundedUser = userFacade.findUserById(1);
        List<Ticket> tickets = bookingFacade.getBookedTickets(foundedUser);
        Assert.assertNotNull(tickets);
        Assert.assertEquals(tickets.size(),4);
        Assert.assertEquals(tickets.get(0).getCategory(), Ticket.Category.PREMIUM);
        Assert.assertEquals(tickets.get(0).getEvent().getId(), 1);
        Assert.assertEquals(tickets.get(0).getPlace(), 12);

    }

    @Test
    public void whenGetBookTicket_thenReturnGetBookTicket(){
        User user = userFacade.findUserById(1);
        List<Ticket> tickets = bookingFacade.getBookedTickets(user);
        Assert.assertNotNull(tickets);
        Assert.assertEquals(tickets.size(),4);
        Assert.assertEquals(tickets.get(0).getCategory(), Ticket.Category.PREMIUM);
        Assert.assertEquals(tickets.get(0).getEvent().getId(), 1);
        Assert.assertEquals(tickets.get(0).getPlace(), 12);
    }

}
