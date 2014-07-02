package com.cdp.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author Andrii_Manieshyn
 */
@Entity
@Table(name="to_do_item_table")
@NamedQueries({
        @NamedQuery(name = "ToDoItem.findByPriority", query = "SELECT tdi FROM ToDoItem tdi WHERE tdi.priority=:priority"),
        @NamedQuery(name = "ToDoItem.findAll", query = "SELECT tdi FROM ToDoItem tdi"),
        @NamedQuery(name = "ToDoItem.findEndingOne", query = "SELECT tdi FROM ToDoItem tdi WHERE tdi.dueDate=:endingDate")
})
public class ToDoItem {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Date dueDate;
    private Date createdDate;
    private Date updatedDate;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
