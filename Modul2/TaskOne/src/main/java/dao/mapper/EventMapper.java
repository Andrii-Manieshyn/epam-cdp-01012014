package dao.mapper;

import model.Event;
import model.impl.EventImpl;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andrii_Manieshyn on 10.04.2014.
 */
public class EventMapper implements ResultSetMapper<Event> {

    @Override
    public Event map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        Event event = new EventImpl();
        event.setId(r.getLong("id"));
        event.setDate(r.getDate("event_date"));
        event.setTitle(r.getString("title"));
        return event;
    }
}
