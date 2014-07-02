package com.cdp.dao;

import com.cdp.entity.Priority;
import com.cdp.entity.ToDoItem;

import java.util.Collection;
import java.util.Date;

/**
 * @author Amdrii_Manieshyn
 */
public interface IToDoItemDao {
    public ToDoItem findById(Long itemId);
    public Collection<ToDoItem> findAll();
    public ToDoItem create(ToDoItem item);
    public ToDoItem update(ToDoItem item);
    public boolean remove(ToDoItem item);
    public Collection<ToDoItem> findByPriority(Priority priority);
    public Collection<ToDoItem> findEndingOne(Date endingDate);
}
