package model;


import javax.persistence.*;

/**
 * @author  Andrii_Manieshyn 10.04.2014.
 */
@Entity(name="ticket")
@Table(name="ticket")
@NamedQueries({
        @NamedQuery(name = "Ticket.allTicketsByUser", query = "SELECT tk FROM ticket tk WHERE tk.user=:user"),
        @NamedQuery(name = "Ticket.allTicketsByEvent", query = "SELECT tk FROM ticket tk WHERE tk.event=:event")
})
public class Ticket{

    public enum Category {
        STANDARD, BAR, PREMIUM
    }

    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Enumerated(EnumType.STRING)
    private Category category;
    private int place;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public String toString(){
        return "Ticket{ id=" + id + ", event=" + event + ", user=" + user +", category=" + category+", place="+ place+" }";
    }
}
