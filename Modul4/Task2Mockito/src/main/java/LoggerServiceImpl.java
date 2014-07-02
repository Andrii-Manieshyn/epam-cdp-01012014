/**
 * Created by Paladin on 20.06.2014.
 */
public class LoggerServiceImpl {

    private FileManager fileManager;

    public void loggeMessage(Client client,Message message){
        fileManager.writeDate(message.getDate());
        fileManager.writeMessage(message.getMessage());
        fileManager.writeRaceType(message.getRaceType());
        fileManager.writeClientName(client.getName());
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }
}
