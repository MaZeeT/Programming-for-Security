package Logic;

import Network.CipherMessage;
import Network.Message;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AESEncryption {
    SecretKeySpec key;
    byte[] iv;
    Cipher cipher;

    public AESEncryption(SecretKeySpec key, String iv) {
        this.key = key;
        this.iv = Hex.decode(iv);
        try {
            this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CipherMessage Encrypt(Message plainMessage) {
        byte[] iv = RandomIV();
        byte[] message = plainMessage.Message().getBytes();
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            message = cipher.doFinal(message);
            return new CipherMessage(plainMessage, message, iv);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public Message Decrypt(CipherMessage cipherMessage){
        byte[] message = cipherMessage.Message().getBytes();
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(cipherMessage.Iv()));
            message = cipher.doFinal(message);
            return new Message(cipherMessage, message);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public byte[] Encrypt(byte[] input) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            return cipher.doFinal(input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] Decrypt(byte[] input){
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            return cipher.doFinal(input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] RandomIV(){
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("DEFAULT", "BC");
            byte[] iv = new byte[16];
            secureRandom.nextBytes(iv);
            return iv;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
