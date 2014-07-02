package dao.factory;

import dao.mapper.EventMapper;
import dao.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

import java.io.IOException;
import java.io.Reader;

/**
 * @author Andrii_Manieshyn on 10.04.2014.
 */
public class ConnectionFactory {

    private SqlSessionFactory sqlSessionFactory;
    private Reader reader;

    public ConnectionFactory() throws IOException {
        reader = Resources.getResourceAsReader("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        sqlSessionFactory.getConfiguration().addMapper(UserMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(EventMapper.class);
    }


    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}
