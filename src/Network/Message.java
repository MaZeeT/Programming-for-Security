package Network;

import java.io.Serializable;

public class Message implements Serializable {
    String username;
    String message;

    public Message(String username, String message){
        this.username = username;
        this.message = message;
    }

    @Override
    public String toString() {
        return username + " says: " + message;
    }
}
