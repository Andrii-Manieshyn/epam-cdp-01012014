import java.util.List;
import java.util.Set;

/**
 * @author Andrii_Manieshyn
 */
public interface Client  {

    public String getName();

    public void setName(String name);

    public List<Message> getMessages();

    public void setMessages(List<Message> messages);

    public Set<Race> getSubsription();

    public void setSubsription(Set<Race> subsription);
}
