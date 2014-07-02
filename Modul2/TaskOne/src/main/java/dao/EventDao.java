package dao;

import dao.mapper.EventMapper;
import model.Event;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * @author Andrii_Manieshyn on 10.04.2014.
 */
@RegisterMapper(EventMapper.class)
public interface EventDao {

    @SqlQuery("SELECT * FROM event WHERE id = :id;")
    public Event read(@Bind("id") long id);
    @SqlQuery("SELECT * FROM event WHERE title = :title;")
    public Event read(@Bind("title") String title);
    @SqlUpdate("UPDATE event SET title=:title, event_date=:date WHERE id=:id;")
    public void update(@BindBean Event event);
    @SqlUpdate("DELETE FROM event WHERE id=:id;")
    public void delete(@Bind("id") long id);
    @SqlUpdate("INSERT INTO event VALUES(:id, :title, :date);")
    public void create(@BindBean Event event);

    public void close();

}
