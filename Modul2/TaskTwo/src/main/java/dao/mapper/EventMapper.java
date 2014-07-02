package dao.mapper;

import model.Event;
import org.apache.ibatis.annotations.*;


/**
 * @author Andrii_Manieshyn on 10.04.2014.
 */
public interface EventMapper {

    static final String SELECT_EVENT_BY_ID = "SELECT * FROM event WHERE id = #{id};";
    static final String SELECT_EVENT_BY_NAME = "SELECT * FROM event WHERE title = #{title};";
    static final String UPDATE_EVENT = "UPDATE event SET title=#{title}, event_date=#{date} WHERE id=#{id};";
    static final String DELETE_EVENT = "DELETE FROM event WHERE id=#{id};";
    static final String INSERT_EVENT = "INSERT INTO event (title, event_date) VALUES (#{title}, #{date})";


    @Select(SELECT_EVENT_BY_ID)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="title", column="title"),
            @Result(property="date", column="event_date")
    })
    public Event readById(long id);

    @Select(SELECT_EVENT_BY_NAME)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="title", column="title"),
            @Result(property="date", column="event_date")
    })
    public Event readByTitle(String title);

    @Update(UPDATE_EVENT)
    public void update(Event event);

    @Delete(DELETE_EVENT)
    public void delete(long id);

    @Insert(INSERT_EVENT)
    public void create(Event event);

}
