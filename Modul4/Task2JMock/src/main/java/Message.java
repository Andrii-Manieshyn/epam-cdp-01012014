import java.util.Date;

/**
 * @author Andrii_Manieshyn
 */
public interface Message {

    public void setMessage(String message);

    public String getMessage();

    public Date getDate();

    public void setDate(Date date);

    public Race getRaceType();

    public void setRaceType(Race raceType);

}
