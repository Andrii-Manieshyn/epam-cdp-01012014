import dao.UserDao;
import junit.framework.Assert;
import model.User;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Andrii_Manieshyn 10.04.2014.
 */

public class UserDaoTest {


    UserDao dao;
    @Before
    public void init() throws SQLException, IOException {

        dao = new UserDao();
    }

    @Test
    public void whenUserDaoReadById_thenReturnUserWithCurrentId(){
        User user = dao.readById(1);

        Assert.assertEquals(user.getName(), "John Smith");
        Assert.assertEquals(user.getId(), 1);
        Assert.assertEquals(user.getEmail(), "smith@gmail.com");
    }

    @Test
    public void whenUserDaoReadByName_thenReturnUserWithCurrentName(){
        User user = dao.readByName("John Smith");

        Assert.assertEquals(user.getName(), "John Smith");
        Assert.assertEquals(user.getId(), 1);
        Assert.assertEquals(user.getEmail(), "smith@gmail.com");
    }

    @Test
    public void whenUserDaoCreate_thenCreateUser(){
        User user = new User();
        user.setEmail("andrew@mail.com");
        user.setName("Andrew");

        dao.create(user);
        User userAndrew = dao.readByName("Andrew");
        Assert.assertEquals(userAndrew.getName(), "Andrew");
        Assert.assertEquals(userAndrew.getEmail(), "andrew@mail.com");
        dao.delete(userAndrew.getId());
    }


    @Test
    public void whenUserDaoDelete_thenDeleteUserWithCurrentId(){
        User user = new User();
        user.setEmail("andrew@mail.com");
        user.setName("Andrew");

        dao.create(user);
        User userAndrew = dao.readByName("Andrew");
        Assert.assertEquals(userAndrew.getName(), "Andrew");
        Assert.assertEquals(userAndrew.getEmail(), "andrew@mail.com");
        dao.delete(userAndrew.getId());

        userAndrew = dao.readByName("Andrew");

        Assert.assertNull(userAndrew);
    }


    @Test
    public void whenUserDaoUpdate_thenUpdateUserWithCurrentId(){
        User user = new User();
        user.setEmail("andrew@mail.com");
        user.setName("Andrew");

        dao.create(user);
        User userAndrew = dao.readByName("Andrew");
        long oldId = userAndrew.getId();

        userAndrew.setName("Pavel");
        dao.update(userAndrew);

        User userPavel = dao.readByName("Pavel");
        Assert.assertEquals(userPavel.getId(), oldId);
        Assert.assertEquals(userPavel.getName(), "Pavel");
        Assert.assertEquals(userPavel.getEmail(), "andrew@mail.com");

        dao.delete(userPavel.getId());

    }



}
