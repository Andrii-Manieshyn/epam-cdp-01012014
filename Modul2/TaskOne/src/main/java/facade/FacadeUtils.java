package facade;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Returns configured instance of Booking facade.
 * Created by maksym_govorischev.
 */
public class FacadeUtils {
    public static BookingFacade getBookingFacade() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"Spring-Datasource.xml"});
        return (BookingFacade) context.getBean("bookingFacadeImpl");
    }
}
