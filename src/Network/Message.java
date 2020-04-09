package Network;

import org.bouncycastle.util.encoders.Hex;

import java.io.Serializable;
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
        this.username = messageToCopy.Username();
        this.message = Hex.toHexString(cipherText);
        this.signature = messageToCopy.Signature();
        this.date = messageToCopy.Date();
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

    public String Username() {
        return username;
    }

    public String Message() {
        return message;
    }

    public byte[] Signature() {
        return signature;
    }

    public Date Date() {
        return date;
    }
}
