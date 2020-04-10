package Network;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class CipherMessage extends Message implements Serializable {
    byte[] iv;
    byte[] cipherText;

    public CipherMessage(Message message, byte[] chatMessage, byte[] iv) {
        super(message.username, new String(chatMessage, StandardCharsets.UTF_8));
        this.cipherText = chatMessage;
        this.iv = iv;
    }

    public byte[] iv(){
        return iv;
    }

    public byte[] cipherText(){
        return cipherText;
    }

}
