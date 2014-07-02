import dao.UserDao;
import junit.framework.Assert;
import model.User;
import model.impl.UserImpl;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Andrii_Manieshyn 10.04.2014.
 */

public class UserDaoTest {


    UserDao dao;
    @Before
    public void init() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());

        DBI dbi = new DBI("jdbc:mysql://127.0.0.1:3306/new_booking_system","root","root");

        dao = dbi.open(UserDao.class);
    }

    @Test
    public void whenUserDaoReadById_thenReturnUserWithCurrentId(){
        User user = dao.read(1);

        Assert.assertEquals(user.getName(), "John Smith");
        Assert.assertEquals(user.getId(), 1);
        Assert.assertEquals(user.getEmail(), "smith@gmail.com");
    }

    @Test
    public void whenUserDaoReadByName_thenReturnUserWithCurrentName(){
        User user = dao.read("John Smith");

        Assert.assertEquals(user.getName(), "John Smith");
        Assert.assertEquals(user.getId(), 1);
        Assert.assertEquals(user.getEmail(), "smith@gmail.com");
    }

    @Test
    public void whenUserDaoCreate_thenCreateUser(){
        UserImpl user = new UserImpl();
        user.setEmail("andrew@mail.com");
        user.setName("Andrew");

        dao.create(user);
        User userAndrew = dao.read("Andrew");
        Assert.assertEquals(userAndrew.getName(), "Andrew");
        Assert.assertEquals(userAndrew.getEmail(), "andrew@mail.com");
        dao.delete(userAndrew.getId());
    }


    @Test
    public void whenUserDaoDelete_thenDeleteUserWithCurrentId(){
        UserImpl user = new UserImpl();
        user.setEmail("andrew@mail.com");
        user.setName("Andrew");

        dao.create(user);
        User userAndrew = dao.read("Andrew");
        Assert.assertEquals(userAndrew.getName(), "Andrew");
        Assert.assertEquals(userAndrew.getEmail(), "andrew@mail.com");
        dao.delete(userAndrew.getId());

        userAndrew = dao.read("Andrew");

        Assert.assertNull(userAndrew);
    }


    @Test
    public void whenUserDaoUpdate_thenUpdateUserWithCurrentId(){
        UserImpl user = new UserImpl();
        user.setEmail("andrew@mail.com");
        user.setName("Andrew");

        dao.create(user);
        User userAndrew = dao.read("Andrew");
        long oldId = userAndrew.getId();

        userAndrew.setName("Pavel");
        dao.update(userAndrew);

        User userPavel = dao.read("Pavel");
        Assert.assertEquals(userPavel.getId(), oldId);
        Assert.assertEquals(userPavel.getName(), "Pavel");
        Assert.assertEquals(userPavel.getEmail(), "andrew@mail.com");

        dao.delete(userPavel.getId());

    }



}
