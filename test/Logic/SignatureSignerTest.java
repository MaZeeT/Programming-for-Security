package Logic;

import Network.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SignatureSignerTest {
    KeyPairGenerator generator;
    KeyPair keyPair;
    RSAPrivateKey privateKey;
    RSAPublicKey publicKey;
    RSAPublicKey falsePublicKey;

    @BeforeEach
    void setUp() {
        try {
            generator = KeyPairGenerator.getInstance("RSA", "BC");

            //initialize the generator with a key size (could be a random size9
            generator.initialize(2048);

            //generate a key pair
            keyPair = generator.generateKeyPair();
            privateKey = (RSAPrivateKey) keyPair.getPrivate();
            publicKey = (RSAPublicKey) keyPair.getPublic();
            falsePublicKey = (RSAPublicKey) generator.generateKeyPair().getPublic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void signMessage() {
        Message message = new Message("signTester", "Hello");

        boolean isSignedBefore = message.signature() != null;
        SignatureSigner.Sign(message, privateKey);
        boolean isSignedAfter = message.signature() != null;

        assertFalse(isSignedBefore);
        assertTrue(isSignedAfter);
    }

    @Test
    void signMessageTwice() {
        Message message = new Message("signTester", "Hello");

        boolean firstSigning = SignatureSigner.Sign(message, privateKey);
        boolean secondSigning = SignatureSigner.Sign(message, privateKey);
        boolean isSigned = message.signature() != null;

        assertTrue(firstSigning);
        assertFalse(secondSigning);
        assertTrue(isSigned);
    }

    @Test
    void verifySignatureTrue() {
        Message message = new Message("signTester", "Hello");

        boolean isSigned = SignatureSigner.Sign(message, privateKey);
        boolean verified = SignatureSigner.Verify(message, publicKey);

        assertTrue(isSigned);
        assertTrue(verified);
    }

    @Test
    void verifySignatureFalse() {
        Message message = new Message("signTester", "Hello");

        boolean isSigned = SignatureSigner.Sign(message, privateKey);
        boolean verified = SignatureSigner.Verify(message, falsePublicKey);

        assertTrue(isSigned);
        assertFalse(verified);
    }
}