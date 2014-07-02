package model;

import javax.persistence.*;
import java.util.List;

/**
 * @author Andrii_Manieshyn 10.04.2014.
 */
@Entity(name = "users")
@Table(name="users")
@NamedQueries({
        @NamedQuery(name = "User.findByName", query = "SELECT us FROM users us WHERE us.name=:name")
})
public class User {

    @Id
    private long id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Ticket> ticketList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @Override
    public String toString(){
        return "User{ id=" + id + ", name" + name + ", email=" + email+"}";
    }
}
