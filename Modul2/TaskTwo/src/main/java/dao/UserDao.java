package dao;

import dao.factory.ConnectionFactory;
import dao.mapper.UserMapper;
import model.User;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

/**
 * @author Andrii_Manieshyn on 10.04.2014.
 */

public class UserDao {

    private ConnectionFactory connectionFactory;

    public UserDao() throws IOException {
        connectionFactory = new ConnectionFactory();
    }
    public User readById(long id){
        SqlSession session = connectionFactory.getSqlSessionFactory().openSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.readById(id);
            return user;
        } finally {
            session.close();
        }
    }

    public User readByName(String name){
        SqlSession session = connectionFactory.getSqlSessionFactory().openSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.readByName(name);
            return user;
        } finally {
            session.close();
        }
    }

    public void update(User user){
        SqlSession session = connectionFactory.getSqlSessionFactory().openSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.update(user);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void delete(long id){
        SqlSession session = connectionFactory.getSqlSessionFactory().openSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.delete(id);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void create(User user){
        SqlSession session = connectionFactory.getSqlSessionFactory().openSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.create(user);
            session.commit();
        } finally {
            session.close();
        }
    }

}
