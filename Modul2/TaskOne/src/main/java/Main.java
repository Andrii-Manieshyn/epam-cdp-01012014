import dao.UserDao;
import facade.BookingFacade;
import facade.FacadeUtils;
import model.Event;
import model.Ticket;
import model.User;
import model.impl.UserImpl;
import org.skife.jdbi.v2.DBI;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Andrii_Manieshyn
 */
public class Main {

    public static void main(String [] args) throws ClassNotFoundException, SQLException {



        BookingFacade bookingFacade = FacadeUtils.getBookingFacade();

        User user = new UserImpl();
        user.setId(1);
        List<Ticket> tickets = bookingFacade.getBookedTickets(user, 4, 1);


        Event event = bookingFacade.getEventById(1);
        System.out.println(event.getTitle());


        List<Event> eventByTitle = bookingFacade.getEventsByTitle("Home Alone",1,1);
        System.out.println(eventByTitle.size());
    }

}
