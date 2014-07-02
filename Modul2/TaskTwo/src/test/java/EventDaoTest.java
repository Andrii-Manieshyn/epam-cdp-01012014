import dao.EventDao;
import junit.framework.Assert;
import model.Event;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author Andrii_Manieshyn 10.04.2014.
 */
public class EventDaoTest {

    EventDao dao;
    @Before
    public void init() throws SQLException, IOException {
        dao = new EventDao();
    }

    @Test
    public void whenEventDaoReadById_thenReturnEventWithCurrentId(){
        Event event = dao.readById(1);

        Assert.assertEquals(event.getTitle(), "Home Alone");
        Assert.assertEquals(event.getId(), 1);
    }

    @Test
    public void whenEventDaoCreateById_thenRCreateEventInDataBase(){
        Date date = new Date();
        Event event = new Event();
        event.setTitle("My title");
        event.setDate(date);


        dao.create(event);
        event =dao.readByTitle("My title");

        Assert.assertEquals(event.getTitle(), "My title");

        dao.delete(event.getId());
    }


    @Test
    public void whenEventDaoDeleteById_thenDeleteEventInDataBase(){
        Date date = new Date();
        Event event = new Event();
        event.setTitle("My title");
        event.setDate(date);


        dao.create(event);
        event =dao.readByTitle("My title");

        Assert.assertEquals(event.getTitle(), "My title");

        dao.delete(event.getId());

        event =dao.readByTitle("My title");

        Assert.assertNull(event);
    }


    @Test
    public void whenEventDaoUpdate_thenUpdateEventInDataBase(){
        Date date = new Date();
        Event event = new Event();
        event.setTitle("My title");
        event.setDate(date);


        dao.create(event);

        event =dao.readByTitle("My title");
        long oldId = event.getId();

        Assert.assertEquals(event.getTitle(), "My title");

        event.setTitle("Other title");
        dao.update(event);
        event =dao.readById(oldId);

        Assert.assertEquals(event.getTitle(), "Other title");
        dao.delete(oldId);
    }
}
