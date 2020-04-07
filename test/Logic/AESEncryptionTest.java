package Logic;

import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AESEncryptionTest {
    AESEncryption aes;

    @BeforeEach
    void setUp() {
        String iv = "9f741fdb5d8845bdb48a94394e84f8a3";

        try {
            char[] password = {'p','a','s','s','w','o','r','d'};
            byte[] salt = {0,1};
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WITHHMACSHA256", "BC");
            byte[] keyBytes = factory.generateSecret(
                    new PBEKeySpec(password, salt, 10, 128)
            ).getEncoded();
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            aes = new AESEncryption(key, iv);
        }catch (Exception ignored){}

    }

    @Test
    void encrypt() {
        byte[] plainText = Hex.decode("a0a1a2a3a4a5a6a7a0a1a2a3a4a5a6a7");
        byte[] cipherText = aes.Encrypt(plainText);

        assertFalse(Arrays.equals(plainText, cipherText));
    }

    @Test
    void decrypt() {
        byte[] plainText = Hex.decode("a0a1a2a3a4a5a6a7a0a1a2a3a4a5a6a7");
        byte[] cipherText = aes.Encrypt(plainText);
        byte[] decryptText = aes.Decrypt(cipherText);

        assertTrue(Arrays.equals(plainText,decryptText));
    }
    
}