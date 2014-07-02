/**
 * @author Andrii_Manieshyn
 */
public interface LoggerService {

    public void loggeMessage(Client clientImpl, Message message);

    public void setFileManager(FileManager fileManager);
}
