package dao;

import dao.mapper.UserMapper;
import model.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * @author Andrii_Manieshyn on 10.04.2014.
 */

@RegisterMapper(UserMapper.class)
public interface UserDao {

    @SqlQuery("SELECT * FROM users WHERE id = :id;")
    public User read(@Bind("id") long id);

    @SqlQuery("SELECT * FROM users WHERE name = :name;")
    public User read(@Bind("name") String name);

    @SqlUpdate("UPDATE users SET name=:name, email=:email WHERE id=:id;")
    public void update(@BindBean User user);

    @SqlUpdate("DELETE FROM users WHERE id=:id;")
    public void delete(@Bind("id") long id);

    @SqlUpdate("INSERT INTO users VALUES(0, :name, :email);")
    public void create(@BindBean User user);

    public void close();
}
