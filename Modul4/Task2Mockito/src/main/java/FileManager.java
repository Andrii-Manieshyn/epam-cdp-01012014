import java.util.Date;

/**
 * Created by Paladin on 20.06.2014.
 */
public interface FileManager {

    public void writeDate(Date date);
    public void writeMessage(String message);
    public void writeRaceType(Race race);
    public void writeClientName(String name);
}
