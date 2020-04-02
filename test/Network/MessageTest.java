package Network;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void signMessageOnce() {
        String username = "signTester";
        String plaintext = "testing message";
        Message message = new Message(username, plaintext);

        byte[] signature = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        boolean firstSign = message.signMessage(signature);

        assertTrue(firstSign);
    }

    @Test
    void signMessageTwice() {
        String username = "signTester";
        String plaintext = "testing message";
        Message message = new Message(username, plaintext);

        byte[] signature = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        boolean firstSign = message.signMessage(signature);
        boolean secondSign = message.signMessage(signature);

        assertTrue(firstSign);
        assertFalse(secondSign);
    }

    @Test
    void verifyMessageTrue() {
        String username = "signTester";
        String plaintext = "testing message";
        Message message = new Message(username, plaintext);

        byte[] signature = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        message.signMessage(signature);
        boolean verified = message.verifyMessage(signature);

        assertTrue(verified);
    }

    @Test
    void verifyMessageFalse() {
        String username = "signTester";
        String plaintext = "testing message";
        Message message = new Message(username, plaintext);

        byte[] signingSignature = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        byte[] verificationSignature = {0, 1, 2, 3, 4, 5, 6, 15};

        message.signMessage(signingSignature);
        boolean verified = message.verifyMessage(verificationSignature);

        assertFalse(verified);
    }

    @Test
    void testToString() {
        String username = "signTester";
        String plaintext = "testing message";
        Message message = new Message(username, plaintext);

        String testString = " - " + username + " says: " + plaintext;

        assertTrue(message.toString().contains(testString));
    }

}