import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Andrii_Manieshyn
 */
public class Client {
    private String name;
    private List<Message> messages;
    private Set<Race> subsription;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Set<Race> getSubsription() {
        return subsription;
    }

    public void setSubsription(Set<Race> subsription) {
        this.subsription = subsription;
    }
}
