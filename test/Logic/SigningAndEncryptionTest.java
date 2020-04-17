package Logic;

import Network.CipherMessage;
import Network.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SigningAndEncryptionTest {
    AESEncryption aes;
    byte[] iv;
    KeyPair keyPair;
    RSAPrivateKey privateKey;
    RSAPublicKey publicKey;
    RSAPublicKey falsePublicKey;

    @BeforeEach
    void setUp() {
        char[] password = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};
        byte[] salt = {0, 1};
        try {
            SecretKeySpec key = KeyMaster.generateSecretKey(password, salt);
            aes = new AESEncryption(key);
            iv = aes.RandomIV();
            keyPair = KeyMaster.generateKeyPair();
            privateKey = (RSAPrivateKey) keyPair.getPrivate();
            publicKey = (RSAPublicKey) keyPair.getPublic();
            falsePublicKey = (RSAPublicKey) KeyMaster.generateKeyPair().getPublic();

        } catch (Exception ignored) {
        }
    }

    @Test
    void encryptAndDecryptMessage() {
        Message plainMessage = new Message("Plain", "chatter");

        CipherMessage cipherMessage = aes.Encrypt(plainMessage);
        Message decryptMessage = aes.Decrypt(cipherMessage);

        assertNotEquals(plainMessage.message(), cipherMessage.message());
        assertNotEquals(cipherMessage.message(), decryptMessage.message());
        assertEquals(plainMessage.message(), decryptMessage.message());
    }

    @Test
    void signAndVerifySignature() {
        Message message = new Message("signTester", "Hello");

        boolean isSigned = SignatureSigner.sign(message, privateKey);
        boolean verified = SignatureSigner.verify(message, publicKey);

        assertTrue(isSigned);
        assertTrue(verified);
    }

    @Test
    void encryptAndDecryptSignedMessage() {
        Message plainMessage = new Message("Plain", "chatter");
        boolean signatureIsNullBefore = plainMessage.signature() == null;
        boolean isSigned = SignatureSigner.sign(plainMessage, privateKey);

        CipherMessage cipherMessage = aes.Encrypt(plainMessage);
        Message decryptMessage = aes.Decrypt(cipherMessage);

        boolean signatureIsNullBetween = cipherMessage.signature() == null;
        boolean signatureIsNullAfter = decryptMessage.signature() == null;
        boolean verified = SignatureSigner.verify(decryptMessage, publicKey);


        assertTrue(signatureIsNullBefore);
        assertTrue(isSigned);
        assertNotEquals(plainMessage.message(), cipherMessage.message());
        assertNotEquals(cipherMessage.message(), decryptMessage.message());
        assertEquals(plainMessage.message(), decryptMessage.message());
        assertFalse(signatureIsNullBetween);
        assertFalse(signatureIsNullAfter);
        assertTrue(verified);
    }

}