import java.util.Date;

/**
 * @author Andrii_Manieshyn
 */
public interface FileManager {

    public void writeDate(Date date);
    public void writeMessage(String message);
    public void writeRaceType(Race race);
    public void writeClientName(String name);
}
