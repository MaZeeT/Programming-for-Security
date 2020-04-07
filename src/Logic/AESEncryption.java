package Logic;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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

}
