package Logic;

import Network.CipherMessage;
import Network.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;

import static org.junit.jupiter.api.Assertions.*;

class AESEncryptionTest {
    AESEncryption aes;
    byte[] iv;

    @BeforeEach
    void setUp() {
        char[] password = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};
        byte[] salt = {0, 1};
        try {
            SecretKeySpec key = KeyMaster.generateSecretKey(password, salt);
            aes = new AESEncryption(key);
            iv = aes.RandomIV();
        } catch (Exception ignored) {
        }
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