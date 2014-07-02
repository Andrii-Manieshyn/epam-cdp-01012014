import model.Ticket;
import model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.TicketService;
import service.UserService;


/**
 * @author  Andrii_Manieshyn 10.04.2014.
 */
public class Main {

    public static void main (String [] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        TicketService ticketService = (TicketService) context.getBean("ticketService");
        boolean j = ticketService.cancelTicket(1);
        System.out.print(j);
    }

}
