import java.util.Date;

/**
 * @author Andrii_Manieshyn
 */
public class Message {
    private String message;
    private Date date;
    private Race raceType;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Race getRaceType() {
        return raceType;
    }

    public void setRaceType(Race raceType) {
        this.raceType = raceType;
    }
}
