package Network;

import org.bouncycastle.util.encoders.Hex;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable {
    final String username;
    final String message;
    byte[] signature;
    final Date date;
    final SimpleDateFormat fDate = new SimpleDateFormat("hh:mm");

    public Message(String username, String message) {
        this.username = username;
        this.message = message;
        this.date = new Date();
    }

    public Message(Message messageToCopy, byte[] cipherText) {
        this.username = messageToCopy.username();
        this.message = new String(cipherText, StandardCharsets.UTF_8);
        this.signature = messageToCopy.signature();
        this.date = messageToCopy.date();
    }

    //System.out.println(new String(decryptText, StandardCharsets.UTF_8));

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

    public String username() {
        return username;
    }

    public String message() {
        return message;
    }

    public byte[] signature() {
        return signature;
    }

    public Date date() {
        return date;
    }
}
