package dao.mapper;

import model.User;
import model.impl.UserImpl;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andrii_Manieshyn on 10.04.2014.
 */
public class UserMapper implements ResultSetMapper<User> {

    @Override
    public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        User user = new UserImpl();
        user.setId(r.getLong("id"));
        user.setName(r.getString("name"));
        user.setEmail(r.getString("email"));
        return user;
    }
}
