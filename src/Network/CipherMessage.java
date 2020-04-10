package Network;

import org.bouncycastle.util.encoders.Hex;

import java.io.Serializable;

public class CipherMessage extends Message implements Serializable {
    byte[] iv;
    byte[] cipherText;

    public CipherMessage(Message message, byte[] chatMessage, byte[] iv) {
        super(message.username, Hex.toHexString(chatMessage));
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
