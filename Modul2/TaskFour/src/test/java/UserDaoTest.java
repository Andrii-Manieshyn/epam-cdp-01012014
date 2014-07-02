import junit.framework.Assert;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**

 */

public class UserDaoTest {

    UserService dao;
    @Before
    public void init() throws SQLException, IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        dao = (UserService) context.getBean("userService");
    }

    @Test
    public void whenUserDaoReadById_thenReturnUserWithCurrentId(){
        User user = dao.findUserById(1);

        Assert.assertEquals(user.getName(), "John Smith");
        Assert.assertEquals(user.getId(), 1);
        Assert.assertEquals(user.getEmail(), "smith@gmail.com");
    }

    @Test
    public void whenUserDaoReadByName_thenReturnUserWithCurrentName(){
        User user = dao.findUserByName("John Smith").get(0);

        Assert.assertEquals(user.getName(), "John Smith");
        Assert.assertEquals(user.getId(), 1);
        Assert.assertEquals(user.getEmail(), "smith@gmail.com");
    }

    @Test
    public void whenUserDaoCreate_thenCreateUser(){
        User user = new User();
        user.setEmail("andrew@mail.com");
        user.setName("Andrew");

        dao.createUser(user);
        User userAndrew = dao.findUserByName("Andrew").get(0);
        Assert.assertEquals(userAndrew.getName(), "Andrew");
        Assert.assertEquals(userAndrew.getEmail(), "andrew@mail.com");
        dao.deleteUser(userAndrew);
    }


    @Test
    public void whenUserDaoDelete_thenDeleteUserWithCurrentId(){
        User user = new User();
        user.setEmail("andrew@mail.com");
        user.setName("Andrew");

        dao.createUser(user);
        User userAndrew = dao.findUserByName("Andrew").get(0);
        Assert.assertEquals(userAndrew.getName(), "Andrew");
        Assert.assertEquals(userAndrew.getEmail(), "andrew@mail.com");
        dao.deleteUser(userAndrew);

        List<User> users = dao.findUserByName("Andrew");

        Assert.assertEquals(users.size(), 0);
    }


    @Test
    public void whenUserDaoUpdate_thenUpdateUserWithCurrentId(){
        User user = new User();
        user.setEmail("andrew@mail.com");
        user.setName("Andrew");

        dao.createUser(user);
        User userAndrew = dao.findUserByName("Andrew").get(0);
        long oldId = userAndrew.getId();

        userAndrew.setName("Pavel");
        dao.updateUser(userAndrew);

        User userPavel = dao.findUserByName("Pavel").get(0);
        Assert.assertEquals(userPavel.getId(), oldId);
        Assert.assertEquals(userPavel.getName(), "Pavel");
        Assert.assertEquals(userPavel.getEmail(), "andrew@mail.com");

        dao.deleteUser(userPavel);

    }



}
