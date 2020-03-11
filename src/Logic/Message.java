package Logic;

import java.io.Serializable;

public class Message implements Serializable {
    private String username;
    private String message;
    private int iv;

    public Message(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public Message(String username, String message, int iv) {
        this.username = username;
        this.message = message;
        this.iv = iv;
    }

    public String getUsername(){
        return username;
    }

    public String getMessage() {
        return message;
    }

    public int getIv() {
        return iv;
    }

    @Override
    public String toString() {
        return message;
    }

}
