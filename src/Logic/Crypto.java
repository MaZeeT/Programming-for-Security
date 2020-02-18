package Logic;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
    SecretKeySpec key;
    Cipher cipher;
    byte[] iv;

    public Crypto(SecretKeySpec key, String iv, String transformation, String provider) throws Exception {
        this.key = key;
        this.cipher = Cipher.getInstance(transformation, provider);
        this.iv = Hex.decode(iv);
    }

    public byte[] Encrypt(byte[] input) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        return cipher.doFinal(input);
    }

    public byte[] Decrypt(byte[] input) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        return cipher.doFinal(input);
    }
}
