package Logic;

import Network.CipherMessage;
import Network.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class AESEncryptionTest {
    AESEncryption aes;
    byte[] iv;

    @BeforeEach
    void setUp() {
        try {
            char[] password = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};
            byte[] salt = {0, 1};
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WITHHMACSHA256", "BC");
            byte[] keyBytes = factory.generateSecret(
                    new PBEKeySpec(password, salt, 10, 128)
            ).getEncoded();
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            aes = new AESEncryption(key);
            iv = aes.RandomIV();
        } catch (Exception ignored) {
        }

    }

    @Test
    void encrypt() {
        String before ="a0a1a2a3a4a5a6a7a0a1a2a3a4a5a6a7";

        byte[] plainText = before.getBytes(StandardCharsets.UTF_8);
        byte[] cipherText = aes.Encrypt(plainText, iv);
        String after = new String(cipherText,StandardCharsets.UTF_8);

        assertNotEquals(before, after);
    }

    @Test
    void decrypt() {
        String before = "Hello with you";

        byte[] plainText = before.getBytes(StandardCharsets.UTF_8);
        byte[] cipherText = aes.Encrypt(plainText, iv);
        byte[] decryptText = aes.Decrypt(cipherText, iv);

        String after = new String(decryptText, StandardCharsets.UTF_8);

        assertEquals(before, after);
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

        assertNotEquals(plainMessage.message(), cipherMessage.message());
        assertNotEquals(cipherMessage.message(), decryptMessage.message());
        assertEquals(plainMessage.message(), decryptMessage.message());
    }

}