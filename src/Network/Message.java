package Network;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable {
    String username;
    String message;
    Date date;
    SimpleDateFormat fDate = new SimpleDateFormat ("hh:mm");

    public Message(String username, String message){
        this.username = username;
        this.message = message;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return fDate.format(date) + " - " + username + " says: " + message;
    }
}
