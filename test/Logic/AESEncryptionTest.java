package Logic;

import Network.CipherMessage;
import Network.Message;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AESEncryptionTest {
    AESEncryption aes;

    @BeforeEach
    void setUp() {
        String iv = "9f741fdb5d8845bdb48a94394e84f8a3";

        try {
            char[] password = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};
            byte[] salt = {0, 1};
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WITHHMACSHA256", "BC");
            byte[] keyBytes = factory.generateSecret(
                    new PBEKeySpec(password, salt, 10, 128)
            ).getEncoded();
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            aes = new AESEncryption(key, iv);
        } catch (Exception ignored) {
        }

    }

    @Test
    void encrypt() {
        byte[] plainText = Hex.decode("a0a1a2a3a4a5a6a7a0a1a2a3a4a5a6a7");
        byte[] cipherText = aes.Encrypt(plainText);

        assertFalse(Arrays.equals(plainText, cipherText));
    }

    @Test
    void decrypt() {


        //byte[] plainText = "hej".getBytes(StandardCharsets.UTF_8);
        //System.out.println(new String(decryptText, StandardCharsets.UTF_8));
        byte[] plainText = Hex.decode("a0a1a2a3a4a5a6a7a0a1a2a3a4a5a6a7");
        byte[] cipherText = aes.Encrypt(plainText);
        byte[] decryptText = aes.Decrypt(cipherText);

        assertTrue(Arrays.equals(plainText, decryptText));
    }

    @Test
    void encryptMessage() {
        Message plainMessage = new Message("Plain", "chatter");

        CipherMessage cipherMessage = aes.Encrypt(plainMessage);

        assertNotEquals(plainMessage.message(), cipherMessage.message());
    }

    @Test
    void decryptCipherMessage() {
        Message plainMessage = new Message("Plain", "chatter");

        CipherMessage cipherMessage = aes.Encrypt(plainMessage);
        Message decryptMessage = aes.Decrypt(cipherMessage);

        System.out.println(plainMessage);
        System.out.println(cipherMessage);
        System.out.println(decryptMessage);

        assertNotEquals(plainMessage.message(), cipherMessage.message());
        assertNotEquals(cipherMessage.message(), decryptMessage.message());
        assertEquals(plainMessage.message(), decryptMessage.message());
    }

}