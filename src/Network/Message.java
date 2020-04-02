package Network;

import java.io.Serializable;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Message implements Serializable {
    String username;
    String message;
    byte[] signature;
    Date date;
    SimpleDateFormat fDate = new SimpleDateFormat("hh:mm");

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

    public boolean verifyMessage(byte[] verificationSignature) {
        return Arrays.equals(signature, verificationSignature);
    }

    @Override
    public String toString() {
        return fDate.format(date) + " - " + username + " says: " + message;
    }
    
}
