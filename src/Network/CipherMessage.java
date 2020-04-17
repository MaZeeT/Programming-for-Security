package Network;

import java.io.Serializable;

public class CipherMessage extends Message implements Serializable {
    byte[] iv;
    byte[] cipherText;

    public CipherMessage(Message message, byte[] cipherText, byte[] iv) {
        super(message, cipherText);
        this.cipherText = cipherText;
        this.iv = iv;
    }

    public byte[] iv(){
        return iv;
    }

    public byte[] cipherText(){
        return cipherText;
    }

}
