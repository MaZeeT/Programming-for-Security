package Network;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
        assertArrayEquals(message.Signature(), signature);
    }

    @Test
    void signMessageTwice() {
        String username = "signTester";
        String plaintext = "testing message";
        Message message = new Message(username, plaintext);

        byte[] signatureA = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        byte[] signatureB = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        boolean firstSign = message.signMessage(signatureA);
        boolean secondSign = message.signMessage(signatureB);

        assertTrue(firstSign);
        assertFalse(secondSign);
        assertTrue(Arrays.equals(message.Signature(),signatureA));
        assertFalse(Arrays.equals(message.Signature(),signatureB));
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