package Network;

import org.bouncycastle.util.encoders.Hex;

import java.io.Serializable;

public class CipherMessage extends Message implements Serializable {
    byte[] iv;

    public CipherMessage(Message message, byte[] chatMessage, byte[] iv) {
        super(message.username, Hex.toHexString(chatMessage));
        this.iv = iv;
    }

    public CipherMessage(Message message, String chatMessage, byte[] iv) {
        super(message.username, chatMessage);
        this.iv = iv;
    }

}
