package Logic;

import java.io.Serializable;

public class Message implements Serializable {
    private String message;
    private int iv;

    public Message(String message) {
        this.message = message;
    }

    public Message(String message, int iv) {
        this.message = message;
        this.iv = iv;
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
