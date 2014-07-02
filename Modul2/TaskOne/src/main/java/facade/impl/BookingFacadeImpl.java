package facade.impl;

import facade.BookingFacade;
import model.Event;
import model.Ticket;
import model.User;
import model.impl.EventImpl;
import model.impl.TicketImpl;
import model.impl.UserImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.Date;
import java.util.List;

/**
 * @author Andrii_Manieshyn on 10.04.2014.
 */
public class BookingFacadeImpl implements BookingFacade {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper;
    private RowMapper<Event> eventMapper;
    private RowMapper<Ticket> bookedTicketsMapper;

    public BookingFacadeImpl() {
        userMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new UserImpl();
                user.setId(resultSet.getLong("id"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                return user;
            }
        };
        eventMapper = new RowMapper<Event>() {
            @Override
            public Event mapRow(ResultSet resultSet, int i) throws SQLException {
                Event event = new EventImpl();
                event.setId(resultSet.getLong("id"));
                event.setDate(resultSet.getDate("event_date"));
                event.setTitle(resultSet.getString("title"));
                return event;
            }
        };
        bookedTicketsMapper = new RowMapper<Ticket>() {
            @Override
            public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
                Ticket ticket = new TicketImpl();
                ticket.setId(resultSet.getLong("id"));
                ticket.setEventId(resultSet.getLong("eventId"));
                ticket.setPlace(resultSet.getInt("place"));
                ticket.setCategory(Ticket.Category.valueOf(resultSet.getString("category")));
                return ticket;
            }
        };
    }

    @Override
    public Event getEventById(long id) {
        String sql = "select * from event where id = ?";
        List<Event> resutlList = jdbcTemplate.query(sql, new Object[]{id}, eventMapper);
        if (resutlList.size()!=0){
            return jdbcTemplate.query(sql, new Object[]{id}, eventMapper).get(0);
        }
        return null;
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        String sql = "select * from event where title = ? limit ?, ?";
        return jdbcTemplate.query(sql, new Object[]{title, pageSize * (pageNum - 1), pageSize}, eventMapper);
    }


    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        String sql = "select * from event where event_date = ? limit ?, ?";
        return jdbcTemplate.query(sql, new Object[]{new java.sql.Date(day.getTime()), pageSize * (pageNum - 1), pageSize}, eventMapper);
    }


    @Override
    public Event createEvent(final Event event) {
        final String sql = "insert into event values(0, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection()
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, event.getTitle());
                preparedStatement.setDate(2, new java.sql.Date(event.getDate().getTime()));
                return preparedStatement;
            }
        }, keyHolder);
        return getEventById(keyHolder.getKey().longValue());
    }

    @Override
    public Event updateEvent(final Event event) {
        final String sql = "UPDATE event SET title= ? , event_date= ? WHERE id = ?";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection()
                        .prepareStatement(sql);
                preparedStatement.setString(1, event.getTitle());
                preparedStatement.setDate(2, new java.sql.Date(event.getDate().getTime()));
                preparedStatement.setLong(3, event.getId());
                return preparedStatement;
            }
        });
        return getEventById(event.getId());
    }

    @Override
    public boolean deleteEvent(final long eventId) {
        final String sql = "DELETE FROM event WHERE id=?";
        int rowEfected = jdbcTemplate.update(new PreparedStatementCreator() {
                                                 @Override
                                                 public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                                     PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection()
                                                             .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                                                     preparedStatement.setLong(1, eventId);
                                                     return preparedStatement;
                                                 }
                                             }
        );
        return rowEfected == 1 ? true : false;
    }

    @Override
    public User getUserById(long userId) {
        String sql = "select * from users where id = ?";
        List<User> resutlList = jdbcTemplate.query(sql, new Object[]{userId}, userMapper);
        if (resutlList.size()!=0){
            return resutlList.get(0);
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "select * from users where email = ?";
        List<User> resutlList = jdbcTemplate.query(sql, new Object[]{email}, userMapper);
        if (resutlList.size()!=0){
            return resutlList.get(0);
        }
        return null;
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        String sql = "select * from users where name = ? LIMIT ? , ?";
        return jdbcTemplate.query(sql, new Object[]{name, pageSize * (pageNum - 1), pageSize}, userMapper);
    }

    @Override
    public User createUser(final User user) {
        final String sql = "insert into users values(0, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection()
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                return preparedStatement;
            }
        }, keyHolder);
        return getUserById(keyHolder.getKey().longValue());
    }

    @Override
    public User updateUser(final User user) {
        final String sql = "UPDATE users SET name=?, email=? WHERE id=?;";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection()
                        .prepareStatement(sql);
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setLong(3, user.getId());
                return preparedStatement;
            }
        });
        return getUserById(user.getId());
    }

    @Override
    public boolean deleteUser(final long userId) {
        final String sql = "DELETE FROM users WHERE id=?";
        int rowEfected = jdbcTemplate.update(new PreparedStatementCreator() {
                                                 @Override
                                                 public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                                     PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection()
                                                             .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                                                     preparedStatement.setLong(1, userId);
                                                     return preparedStatement;
                                                 }
                                             }
        );
        return rowEfected == 1 ? true : false;
    }

    @Override
    public Ticket bookTicket(final long userId, final long eventId, final int place, final Ticket.Category category) {
        final String sql = "insert into Ticket values(0, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection()
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setLong(1, userId);
                preparedStatement.setLong(2, eventId);
                preparedStatement.setString(3, category.toString().toUpperCase());
                preparedStatement.setInt(4, place);
                return preparedStatement;
            }
        }, keyHolder);
        return getbookTicket(keyHolder.getKey().longValue());
    }

    public Ticket getbookTicket(final long ticketId) {
        String sql = "select * from ticket where id = ?";
        return jdbcTemplate.query(sql, new Object[]{ticketId}, bookedTicketsMapper).get(0);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        String sql = "select id, eventId, place, category from ticket where userId = ? LIMIT ?, ?";
        return jdbcTemplate.query(sql, new Object[]{user.getId(), pageSize * (pageNum - 1), pageSize}, bookedTicketsMapper);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        String sql = "select id, eventId, place, category from ticket where eventId = ? LIMIT ?, ?";
        return jdbcTemplate.query(sql, new Object[]{event.getId(), pageSize * (pageNum - 1), pageSize}, bookedTicketsMapper);
    }

    @Override
    public boolean cancelTicket(final long ticketId) {
        final String sql = "DELETE FROM ticket WHERE id=?";
        int rowEfected = jdbcTemplate.update(new PreparedStatementCreator() {
                                                 @Override
                                                 public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                                     PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection()
                                                             .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                                                     preparedStatement.setLong(1, ticketId);
                                                     return preparedStatement;
                                                 }
                                             }
        );
        return rowEfected == 1 ? true : false;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
