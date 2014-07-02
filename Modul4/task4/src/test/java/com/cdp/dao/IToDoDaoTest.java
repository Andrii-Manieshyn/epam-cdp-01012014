package com.cdp.dao;

import com.cdp.entity.Priority;
import com.cdp.entity.ToDoItem;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.Collection;

/**
 * @author Andrii_Manieshyn
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:**/spring-context.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class IToDoDaoTest {

    @Autowired
    public IToDoItemDaoImpl iToDoItemDao;

    private static final String JDBC_DRIVER = com.mysql.jdbc.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/task3testing";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String SCHEMA_LOCATION="src/test/java/resources/schema.sql";
    private static final String DATASET_LOCATION="/dataset.xml";

    @BeforeClass
    public static void createSchema() throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        ScriptRunner scriptRunner= new ScriptRunner(connection);
        scriptRunner.runScript(new BufferedReader(new FileReader(SCHEMA_LOCATION)));
        connection.commit();
        connection.close();
    }


    private void cleanlyInsertDataset(IDataSet dataSet) throws Exception {
        IDatabaseTester databaseTester = new JdbcDatabaseTester(
                JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    private IDataSet readDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File(DATASET_LOCATION));
    }

    @Test
    @DatabaseSetup(value = DATASET_LOCATION)
    public void whenGetItemById_thenReturnValidItem(){
        Long timeStart = System.currentTimeMillis();
        ToDoItem foundedToDoItem = iToDoItemDao.findById(1l);
        Assert.assertTrue(foundedToDoItem.getDescription().equals("description one"));
        Assert.assertTrue(foundedToDoItem.getTitle().equals("titleOne"));
        Assert.assertTrue(foundedToDoItem.getPriority().equals(Priority.RUSH));

        Assert.assertEquals(foundedToDoItem.getDueDate(), Date.valueOf("2014-02-04"));
        Assert.assertEquals(foundedToDoItem.getCreatedDate(), Date.valueOf("2014-01-02"));
        Assert.assertEquals(foundedToDoItem.getUpdatedDate(), Date.valueOf("2014-01-02"));

        System.out.println("whenGetItemById_thenReturnValidItem >> " + (System.currentTimeMillis() - timeStart));
    }

    @Test
    @Rollback(value = true)
    @DatabaseSetup(value = DATASET_LOCATION)
    public void whenCreateExecuted_thenItemCreatedInDatabase(){
        Long timeStart = System.currentTimeMillis();
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setDescription("Test description");
        toDoItem.setTitle("Test title");
        toDoItem.setPriority(Priority.CRITICAL);

        iToDoItemDao.create(toDoItem);

        Assert.assertTrue(toDoItem.getId() != null);
        Long idNewlyCreatedItem = toDoItem.getId();
        ToDoItem foudedToDoItem = iToDoItemDao.findById(idNewlyCreatedItem);
        Assert.assertTrue(foudedToDoItem.getDescription().equals("Test description"));
        Assert.assertTrue(foudedToDoItem.getTitle().equals("Test title"));
        Assert.assertTrue(foudedToDoItem.getPriority().equals(Priority.CRITICAL));
        System.out.println("whenCreateExecuted_thenItemCreatedInDatabase >> " + (System.currentTimeMillis() - timeStart));
    }

    @Test
    @Rollback(value = true)
    @DatabaseSetup(value = DATASET_LOCATION)
    public void whenUpdateItem_thenItemUpdatedInDatabase(){
        Long timeStart = System.currentTimeMillis();
        ToDoItem foundedToDoItem = iToDoItemDao.findById(1l);
        Assert.assertTrue(foundedToDoItem.getDescription().equals("description one"));
        Assert.assertTrue(foundedToDoItem.getTitle().equals("titleOne"));
        Assert.assertTrue(foundedToDoItem.getPriority().equals(Priority.RUSH));

        Assert.assertEquals(foundedToDoItem.getDueDate(), Date.valueOf("2014-02-04"));
        Assert.assertEquals(foundedToDoItem.getCreatedDate(), Date.valueOf("2014-01-02"));
        Assert.assertEquals(foundedToDoItem.getUpdatedDate(), Date.valueOf("2014-01-02"));

        foundedToDoItem.setDescription("description one updated");
        foundedToDoItem.setTitle("titleOne updated");
        foundedToDoItem.setPriority(Priority.LOW);

        foundedToDoItem.setDueDate(Date.valueOf("2015-02-04"));
        foundedToDoItem.setCreatedDate(Date.valueOf("2015-01-02"));
        foundedToDoItem.setUpdatedDate(Date.valueOf("2015-01-02"));

        iToDoItemDao.update(foundedToDoItem);

        foundedToDoItem = iToDoItemDao.findById(1l);

        Assert.assertTrue(foundedToDoItem.getDescription().equals("description one updated"));
        Assert.assertTrue(foundedToDoItem.getTitle().equals("titleOne updated"));
        Assert.assertTrue(foundedToDoItem.getPriority().equals(Priority.LOW));

        Assert.assertEquals(foundedToDoItem.getDueDate(), Date.valueOf("2015-02-04"));
        Assert.assertEquals(foundedToDoItem.getCreatedDate(), Date.valueOf("2015-01-02"));
        Assert.assertEquals(foundedToDoItem.getUpdatedDate(), Date.valueOf("2015-01-02"));
        System.out.println("whenUpdateItem_thenItemUpdatedInDatabase >> " + (System.currentTimeMillis() - timeStart));
    }

    @Test
    @Rollback(value = true)
    @DatabaseSetup(value = DATASET_LOCATION)
    public void whenRemoveItem_thenItemRemovedInDatabase(){
        Long timeStart = System.currentTimeMillis();
        ToDoItem foundedToDoItem = iToDoItemDao.findById(1l);
        Assert.assertTrue(foundedToDoItem.getDescription().equals("description one"));
        Assert.assertTrue(foundedToDoItem.getTitle().equals("titleOne"));
        Assert.assertTrue(foundedToDoItem.getPriority().equals(Priority.RUSH));

        Assert.assertEquals(foundedToDoItem.getDueDate(), Date.valueOf("2014-02-04"));
        Assert.assertEquals(foundedToDoItem.getCreatedDate(), Date.valueOf("2014-01-02"));
        Assert.assertEquals(foundedToDoItem.getUpdatedDate(), Date.valueOf("2014-01-02"));

        foundedToDoItem.setDescription("description one updated");
        foundedToDoItem.setTitle("titleOne updated");
        foundedToDoItem.setPriority(Priority.LOW);

        foundedToDoItem.setDueDate(Date.valueOf("2015-02-04"));
        foundedToDoItem.setCreatedDate(Date.valueOf("2015-01-02"));
        foundedToDoItem.setUpdatedDate(Date.valueOf("2015-01-02"));

        iToDoItemDao.remove(foundedToDoItem);

        foundedToDoItem = iToDoItemDao.findById(1l);

        Assert.assertNull(foundedToDoItem);
        System.out.println("whenRemoveItem_thenItemRemovedInDatabase >> " + (System.currentTimeMillis() - timeStart));
    }

    @Test
    @DatabaseSetup(value = DATASET_LOCATION)
    public void whenFindAllItem_thenReturnFullCollection(){
        Long timeStart = System.currentTimeMillis();
        Collection<ToDoItem> foundedAllToDoItem = iToDoItemDao.findAll();
        Assert.assertTrue(foundedAllToDoItem.size()==5);
        for (ToDoItem toDoItem : foundedAllToDoItem) {
            if (toDoItem.getId() == 1) {
                Assert.assertTrue(toDoItem.getDescription().equals("description one"));
                Assert.assertTrue(toDoItem.getTitle().equals("titleOne"));
                Assert.assertTrue(toDoItem.getPriority().equals(Priority.RUSH));

                Assert.assertEquals(toDoItem.getDueDate(), Date.valueOf("2014-02-04"));
                Assert.assertEquals(toDoItem.getCreatedDate(), Date.valueOf("2014-01-02"));
                Assert.assertEquals(toDoItem.getUpdatedDate(), Date.valueOf("2014-01-02"));
            } else
            if (toDoItem.getId() == 2) {
                Assert.assertTrue(toDoItem.getDescription().equals("description two"));
                Assert.assertTrue(toDoItem.getTitle().equals("titleTwo"));
                Assert.assertTrue(toDoItem.getPriority().equals(Priority.NORMAL));

                Assert.assertEquals(toDoItem.getDueDate(), Date.valueOf("2013-02-04"));
                Assert.assertEquals(toDoItem.getCreatedDate(), Date.valueOf("2013-01-02"));
                Assert.assertEquals(toDoItem.getUpdatedDate(), Date.valueOf("2013-01-02"));
            } else
            if (toDoItem.getId() == 3) {
                Assert.assertTrue(toDoItem.getDescription().equals("description three"));
                Assert.assertTrue(toDoItem.getTitle().equals("titleThree"));
                Assert.assertTrue(toDoItem.getPriority().equals(Priority.LOW));

                Assert.assertEquals(toDoItem.getDueDate(), Date.valueOf("2012-02-04"));
                Assert.assertEquals(toDoItem.getCreatedDate(), Date.valueOf("2012-01-02"));
                Assert.assertEquals(toDoItem.getUpdatedDate(), Date.valueOf("2012-01-02"));
            } else
            if (toDoItem.getId() == 4) {
                Assert.assertTrue(toDoItem.getDescription().equals("description four"));
                Assert.assertTrue(toDoItem.getTitle().equals("titleFour"));
                Assert.assertTrue(toDoItem.getPriority().equals(Priority.NORMAL));

                Assert.assertEquals(toDoItem.getDueDate(), Date.valueOf("2011-02-04"));
                Assert.assertEquals(toDoItem.getCreatedDate(), Date.valueOf("2011-01-02"));
                Assert.assertEquals(toDoItem.getUpdatedDate(), Date.valueOf("2011-01-02"));
            } else
            if (toDoItem.getId() == 5) {
                Assert.assertTrue(toDoItem.getDescription().equals("description five"));
                Assert.assertTrue(toDoItem.getTitle().equals("titleFive"));
                Assert.assertTrue(toDoItem.getPriority().equals(Priority.CRITICAL));

                Assert.assertEquals(toDoItem.getDueDate(), Date.valueOf("2014-02-04"));
                Assert.assertEquals(toDoItem.getCreatedDate(), Date.valueOf("2014-01-02"));
                Assert.assertEquals(toDoItem.getUpdatedDate(), Date.valueOf("2014-01-02"));
            } else {
                Assert.fail();
            }
        }
        System.out.println("whenDinfAllItem_thenReturnFullCollection >> " + (System.currentTimeMillis() - timeStart));
    }


    @Test
    @DatabaseSetup(value = DATASET_LOCATION)
    public void whenFindByPriorityItem_thenReturnCollectionWithSettedPriority(){
        Long timeStart = System.currentTimeMillis();
        Collection<ToDoItem> foundedByPriorityToDoItem = iToDoItemDao.findByPriority(Priority.NORMAL);
        Assert.assertTrue(foundedByPriorityToDoItem.size()==2);
        for (ToDoItem toDoItem : foundedByPriorityToDoItem) {
            if (toDoItem.getId() == 2) {
                Assert.assertTrue(toDoItem.getDescription().equals("description two"));
                Assert.assertTrue(toDoItem.getTitle().equals("titleTwo"));
                Assert.assertTrue(toDoItem.getPriority().equals(Priority.NORMAL));

                Assert.assertEquals(toDoItem.getDueDate(), Date.valueOf("2013-02-04"));
                Assert.assertEquals(toDoItem.getCreatedDate(), Date.valueOf("2013-01-02"));
                Assert.assertEquals(toDoItem.getUpdatedDate(), Date.valueOf("2013-01-02"));
            } else
            if (toDoItem.getId() == 4) {
                Assert.assertTrue(toDoItem.getDescription().equals("description four"));
                Assert.assertTrue(toDoItem.getTitle().equals("titleFour"));
                Assert.assertTrue(toDoItem.getPriority().equals(Priority.NORMAL));

                Assert.assertEquals(toDoItem.getDueDate(), Date.valueOf("2011-02-04"));
                Assert.assertEquals(toDoItem.getCreatedDate(), Date.valueOf("2011-01-02"));
                Assert.assertEquals(toDoItem.getUpdatedDate(), Date.valueOf("2011-01-02"));
            } else{
                Assert.fail();
            }
        }
        foundedByPriorityToDoItem = iToDoItemDao.findByPriority(Priority.LOW);
        Assert.assertTrue(foundedByPriorityToDoItem.size() == 1);
        for (ToDoItem toDoItem : foundedByPriorityToDoItem) {
            if (toDoItem.getId() == 3) {
                Assert.assertTrue(toDoItem.getDescription().equals("description three"));
                Assert.assertTrue(toDoItem.getTitle().equals("titleThree"));
                Assert.assertTrue(toDoItem.getPriority().equals(Priority.LOW));

                Assert.assertEquals(toDoItem.getDueDate(), Date.valueOf("2012-02-04"));
                Assert.assertEquals(toDoItem.getCreatedDate(), Date.valueOf("2012-01-02"));
                Assert.assertEquals(toDoItem.getUpdatedDate(), Date.valueOf("2012-01-02"));
            }  else{
                Assert.fail();
            }
        }
        System.out.println("whenFindByPriorityItem_thenReturnCollectionWithSettedPriority >> " + (System.currentTimeMillis() - timeStart));
    }


    @Test
    @DatabaseSetup(value = DATASET_LOCATION)
    public void whenFindByEndDate_thenReturnCollectionWithSettedEndDate(){
        Long timeStart = System.currentTimeMillis();
        Collection<ToDoItem> foundedByPriorityToDoItem = iToDoItemDao.findEndingOne(Date.valueOf("2014-02-04"));
        Assert.assertTrue(foundedByPriorityToDoItem.size()==2);
        for (ToDoItem toDoItem : foundedByPriorityToDoItem) {
            if (toDoItem.getId() == 1) {
                Assert.assertTrue(toDoItem.getDescription().equals("description one"));
                Assert.assertTrue(toDoItem.getTitle().equals("titleOne"));
                Assert.assertTrue(toDoItem.getPriority().equals(Priority.RUSH));

                Assert.assertEquals(toDoItem.getDueDate(), Date.valueOf("2014-02-04"));
                Assert.assertEquals(toDoItem.getCreatedDate(), Date.valueOf("2014-01-02"));
                Assert.assertEquals(toDoItem.getUpdatedDate(), Date.valueOf("2014-01-02"));
            } else
            if (toDoItem.getId() == 5) {
                Assert.assertTrue(toDoItem.getDescription().equals("description five"));
                Assert.assertTrue(toDoItem.getTitle().equals("titleFive"));
                Assert.assertTrue(toDoItem.getPriority().equals(Priority.CRITICAL));

                Assert.assertEquals(toDoItem.getDueDate(), Date.valueOf("2014-02-04"));
                Assert.assertEquals(toDoItem.getCreatedDate(), Date.valueOf("2014-01-02"));
                Assert.assertEquals(toDoItem.getUpdatedDate(), Date.valueOf("2014-01-02"));
            } else{
                Assert.fail();
            }
        }
        System.out.println("whenFindByEndDate_thenReturnCollectionWithSettedEndDate >> " + (System.currentTimeMillis() - timeStart));
    }

}
