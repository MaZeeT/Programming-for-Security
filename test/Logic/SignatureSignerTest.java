package Logic;

import Network.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SignatureSignerTest {
    KeyPair keyPair;
    RSAPrivateKey privateKey;
    RSAPublicKey publicKey;
    RSAPublicKey falsePublicKey;

    @BeforeEach
    void setUp() {
        try {
            keyPair = KeyMaster.generateKeyPair();
            privateKey = (RSAPrivateKey) keyPair.getPrivate();
            publicKey = (RSAPublicKey) keyPair.getPublic();
            falsePublicKey = (RSAPublicKey) KeyMaster.generateKeyPair().getPublic();
        } catch (Exception ignored) {
        }
    }

    @Test
    void signMessage() {
        Message message = new Message("signTester", "Hello");

        boolean isSignedBefore = message.signature() != null;
        SignatureSigner.sign(message, privateKey);
        boolean isSignedAfter = message.signature() != null;

        assertFalse(isSignedBefore);
        assertTrue(isSignedAfter);
    }

    @Test
    void signMessageTwice() {
        Message message = new Message("signTester", "Hello");

        boolean firstSigning = SignatureSigner.sign(message, privateKey);
        boolean secondSigning = SignatureSigner.sign(message, privateKey);
        boolean isSigned = message.signature() != null;

        assertTrue(firstSigning);
        assertFalse(secondSigning);
        assertTrue(isSigned);
    }

    @Test
    void verifySignatureTrue() {
        Message message = new Message("signTester", "Hello");

        boolean isSigned = SignatureSigner.sign(message, privateKey);
        boolean verified = SignatureSigner.verify(message, publicKey);

        assertTrue(isSigned);
        assertTrue(verified);
    }

    @Test
    void verifySignatureFalse() {
        Message message = new Message("signTester", "Hello");

        boolean isSigned = SignatureSigner.sign(message, privateKey);
        boolean verified = SignatureSigner.verify(message, falsePublicKey);

        assertTrue(isSigned);
        assertFalse(verified);
    }
}