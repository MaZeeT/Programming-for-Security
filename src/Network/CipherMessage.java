package Network;

import java.io.Serializable;

public class CipherMessage extends Message implements Serializable {
    byte[] cipherText;
    byte[] iv;

    public CipherMessage(Message message, byte[] cipherText, byte[] iv) {
        super(message, cipherText);
        this.cipherText = cipherText;
        this.iv = iv;
    }

    public byte[] cipherText() {
        return cipherText;
    }

    public byte[] iv() {
        return iv;
    }

}
