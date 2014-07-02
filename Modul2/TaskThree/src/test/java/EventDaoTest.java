import junit.framework.Assert;
import model.Event;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.EventService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author Andrii_Manieshyn 10.04.2014.
 */
public class EventDaoTest {

    EventService dao;
    @Before
    public void init() throws SQLException, IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        dao = (EventService) context.getBean("eventService");
    }

    @Test
    public void whenEventDaoReadById_thenReturnEventWithCurrentId(){
        Event event = dao.findEventById(1);

        Assert.assertEquals(event.getTitle(), "Home Alone");
        Assert.assertEquals(event.getId(), 1);
    }

    @Test
    public void whenEventDaoCreateById_thenRCreateEventInDataBase(){
        Date date = new Date();
        Event event = new Event();
        event.setTitle("My title");
        event.setDate(date);


        dao.createEvent(event);
        event =dao.findEventByTitle("My title").get(0);

        Assert.assertEquals(event.getTitle(), "My title");

        dao.deleteeEvent(event);
    }


    @Test
    public void whenEventDaoDeleteById_thenDeleteEventInDataBase(){
        Date date = new Date();
        Event event = new Event();
        event.setTitle("My title");
        event.setDate(date);


        dao.createEvent(event);
        event =dao.findEventByTitle("My title").get(0);

        Assert.assertEquals(event.getTitle(), "My title");

        dao.deleteeEvent(event);

        List<Event> events =dao.findEventByTitle("My title");

        Assert.assertEquals(events.size(), 0);
    }


    @Test
    public void whenEventDaoUpdate_thenUpdateEventInDataBase(){
        Date date = new Date();
        Event event = new Event();
        event.setTitle("My title");
        event.setDate(date);


        dao.createEvent(event);

        event =dao.findEventByTitle("My title").get(0);
        long oldId = event.getId();

        Assert.assertEquals(event.getTitle(), "My title");

        event.setTitle("Other title");
        dao.updateEvent(event);
        event =dao.findEventById(oldId);

        Assert.assertEquals(event.getTitle(), "Other title");
        dao.deleteeEvent(event);
    }
}
