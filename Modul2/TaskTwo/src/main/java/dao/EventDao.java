package dao;

import dao.factory.ConnectionFactory;
import dao.mapper.EventMapper;
import model.Event;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

/**
 * @author Andrii_Manieshyn on 10.04.2014.
 */
public class EventDao {

    private ConnectionFactory connectionFactory;

    public EventDao() throws IOException {
        connectionFactory = new ConnectionFactory();
    }

    public Event readById(long id) {
        SqlSession session = connectionFactory.getSqlSessionFactory().openSession();
        try {
            EventMapper mapper = session.getMapper(EventMapper.class);
            Event event = mapper.readById(id);
            return event;
        } finally {
            session.close();
        }
    }

    public Event readByTitle(String title){
        SqlSession session = connectionFactory.getSqlSessionFactory().openSession();
        try {
            EventMapper mapper = session.getMapper(EventMapper.class);
            Event event = mapper.readByTitle(title);
            return event;
        } finally {
            session.close();
        }
    }

    public void update(Event event){
        SqlSession session = connectionFactory.getSqlSessionFactory().openSession();
        try {
            EventMapper mapper = session.getMapper(EventMapper.class);
            mapper.update(event);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void delete(long id){
        SqlSession session = connectionFactory.getSqlSessionFactory().openSession();
        try {
            EventMapper mapper = session.getMapper(EventMapper.class);
            mapper.delete(id);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void create(Event event){
        SqlSession session = connectionFactory.getSqlSessionFactory().openSession();
        try {
            EventMapper mapper = session.getMapper(EventMapper.class);
            mapper.create(event);
            session.commit();
        } finally {
            session.close();
        }
    }

}
