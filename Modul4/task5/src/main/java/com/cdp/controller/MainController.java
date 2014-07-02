package com.cdp.controller;

import com.cdp.dao.IToDoItemDao;
import com.cdp.entity.Priority;
import com.cdp.entity.ToDoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Collection;

/**
 * @author Andrii_Manieshyn
 */
@Controller
@RequestMapping("/get")
public class MainController {

    @Autowired
    private IToDoItemDao iToDoItemDao;

    @ResponseBody
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public ToDoItem getItemById(@PathVariable Long id){
        if (id != null) {
            return iToDoItemDao.findById(id);
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Collection<ToDoItem> getAllItems(){
        return iToDoItemDao.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/getByPriority/{priority}", method = RequestMethod.GET)
    public Collection<ToDoItem> getItemsByPriority(@PathVariable Priority priority){
        if (priority != null) {
            return iToDoItemDao.findByPriority(priority);
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ToDoItem createItem (@RequestParam(value="title", required = true) String title,
                                            @RequestParam(value="description", required = true) String description,
                                            @RequestParam(value="dueDate", required = true) Date dueDate,
                                            @RequestParam(value="createdDate", required = true) Date createdDate,
                                            @RequestParam(value="updatedDate", required = true) Date updatedDate,
                                            @RequestParam(value="priority", required = true) Priority priority){
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setTitle(title);
        toDoItem.setDescription(description);
        toDoItem.setDueDate(dueDate);
        toDoItem.setCreatedDate(createdDate);
        toDoItem.setUpdatedDate(updatedDate);
        toDoItem.setPriority(priority);
        return iToDoItemDao.create(toDoItem);
    }

    @ResponseBody
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ToDoItem updateItem (@PathVariable Long id,
                                @RequestParam(value="title", required = false) String title,
                                @RequestParam(value="description", required = false) String description,
                                @RequestParam(value="dueDate", required = false) Date dueDate,
                                @RequestParam(value="createdDate", required = false) Date createdDate,
                                @RequestParam(value="updatedDate", required = false) Date updatedDate,
                                @RequestParam(value="priority", required = false) Priority priority){
        ToDoItem toDoItem = iToDoItemDao.findById(id);
        if (title != null) {
            toDoItem.setTitle(title);
        }
        if (description != null) {
            toDoItem.setDescription(description);
        }
        if (dueDate != null) {
            toDoItem.setDueDate(dueDate);
        }
        if (createdDate != null) {
            toDoItem.setCreatedDate(createdDate);
        }
        if (updatedDate != null) {
            toDoItem.setUpdatedDate(updatedDate);
        }
        if (priority != null) {
            toDoItem.setPriority(priority);
        }
        return iToDoItemDao.update(toDoItem);
    }

    @ResponseBody
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public boolean remove(@PathVariable Long id){
        if (id != null) {
            return iToDoItemDao.remove(iToDoItemDao.findById(id));
        } else {
            return false;
        }
    }

}
