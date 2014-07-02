import model.Event;
import model.Ticket;
import model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.EventService;
import service.TicketService;
import service.UserService;

import java.util.Date;
import java.util.List;


/**
 * @author  Andrii_Manieshyn 10.04.2014.
 */
public class Main {

    public static void main (String [] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        EventService eventService = (EventService) context.getBean("eventService");
    }

}
