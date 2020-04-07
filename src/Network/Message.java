package Network;

import java.io.Serializable;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Message implements Serializable {
    private String username;
    private String message;
    private byte[] signature;
    private Date date;
    private SimpleDateFormat fDate = new SimpleDateFormat("hh:mm");

    public Message(String username, String message) {
        this.username = username;
        this.message = message;
        this.date = new Date();
    }

    public boolean signMessage(byte[] signature) {
        if (this.signature == null) {
            this.signature = signature;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return fDate.format(date) + " - " + username + " says: " + message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public byte[] getSignature() {
        return signature;
    }

    public Date getDate() {
        return date;
    }
}
