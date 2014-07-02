/**
 * @author Andrii_Manieshyn
 */
public class LoggerServiceImpl implements LoggerService {

    private FileManager fileManager;

    public void loggeMessage(Client clientImpl, Message message){
        fileManager.writeDate(message.getDate());
        fileManager.writeMessage(message.getMessage());
        fileManager.writeRaceType(message.getRaceType());
        fileManager.writeClientName(clientImpl.getName());
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }
}
