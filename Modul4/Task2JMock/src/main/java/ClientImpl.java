import java.util.List;
import java.util.Set;

/**
 * @author Andrii_Manieshyn
 */
public class ClientImpl implements Client {
    private String name;
    private List<Message> messages;
    private Set<Race> subsription;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public Set<Race> getSubsription() {
        return subsription;
    }

    @Override
    public void setSubsription(Set<Race> subsription) {
        this.subsription = subsription;
    }
}
