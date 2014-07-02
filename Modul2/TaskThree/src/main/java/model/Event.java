package model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author  Andrii_Manieshyn 10.04.2014.
 */
@Entity(name="event")
@Table(name="event")
@NamedQueries({
        @NamedQuery(name = "Event.findByTitle", query = "SELECT ev FROM event ev WHERE ev.title=:title"),
        @NamedQuery(name = "Event.findByDate", query = "SELECT ev FROM event ev WHERE ev.date=:date")
})
public class Event {

    @Id
    private long id;
    private String title;

    @Column(name = "event_date")
    private Date date;

    @OneToMany(mappedBy = "event")
    private List<Ticket> ticketList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return "Event{ id=" + id + ", title" + title + ", date=" + date+"}";
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
}
