package dao.mapper;

import model.User;
import org.apache.ibatis.annotations.*;


/**
 * @author Andrii_Manieshyn on 10.04.2014.
 */
public interface UserMapper {

    static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = #{id};";
    static final String SELECT_USER_BY_NAME = "SELECT * FROM users WHERE name = #{name};";
    static final String UPDATE_USER = "UPDATE users SET name=#{name}, email=#{email} WHERE id=#{id};";
    static final String DELETE_USER = "DELETE FROM users WHERE id=#{id};";
    static final String INSERT_USER = "INSERT INTO users (email, name) VALUES (#{email}, #{name})";

    @Select(SELECT_USER_BY_ID)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="name", column="name"),
            @Result(property="email", column="email")
    })
    public User readById(long id);

    @Select(SELECT_USER_BY_NAME)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="name", column="name"),
            @Result(property="email", column="email")
    })
    public User readByName(String name);

    @Update(UPDATE_USER)
    public void update(User user);

    @Delete(DELETE_USER)
    public void delete(long id);

    @Insert(INSERT_USER)
    public void create(User user);
}
