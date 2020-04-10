package Logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.*;

class KeyMasterTest {
    SecretKeySpec key;
    KeyPair keyPair;
    KeyMaster keyMaster;

    @BeforeEach
    void setUp() {
        char[] password = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};
        byte[] salt = {0, 1};
      //  key = KeyMaster.generateSecretKey(password, salt);
        KeyPair keyPair;
        // keyMaster = new KeyMaster(key, );
    }

    @Test
    void symmetricKeySamePasswords() {
        char[] passwordA = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd', '6'};
        char[] passwordB = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd', '6'};
        byte[] salt = {0, 6, 4, 8};
        SecretKeySpec keyA = null;
        SecretKeySpec keyB = null;

        try {
            keyA = KeyMaster.generateSecretKey(passwordA, salt);
            keyB = KeyMaster.generateSecretKey(passwordB, salt);
        } catch (Exception ignored) {
        }

        assertNotNull(keyA);
        assertNotNull(keyB);
        assertEquals(keyA, keyB);
    }

    @Test
    void symmetricKeyDifferentPasswords() {
        char[] passwordA = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd', 'A'};
        char[] passwordB = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd', 'B'};
        byte[] salt = {0, 1, 6, 4, 8};
        SecretKeySpec keyA = null;
        SecretKeySpec keyB = null;

        try {
            keyA = KeyMaster.generateSecretKey(passwordA, salt);
            keyB = KeyMaster.generateSecretKey(passwordB, salt);
        } catch (Exception ignored) {
        }

        assertNotNull(keyA);
        assertNotNull(keyB);
        assertNotEquals(keyA, keyB);
    }

    @Test
    void symmetricKeySameSalts() {
        char[] password = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd', 'S', 'A', 'L', 'T'};
        byte[] saltA = {0, 1, 6, 4, 8, 8, 9, 3};
        byte[] saltB = {0, 1, 6, 4, 8, 8, 9, 3};
        SecretKeySpec keyA = null;
        SecretKeySpec keyB = null;

        try {
            keyA = KeyMaster.generateSecretKey(password, saltA);
            keyB = KeyMaster.generateSecretKey(password, saltB);
        } catch (Exception ignored) {
        }

        assertNotNull(keyA);
        assertNotNull(keyB);
        assertEquals(keyA, keyB);
    }

    @Test
    void symmetricKeyDifferentSalts() {
        char[] password = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd', 'S', 'A', 'L', 'T'};
        byte[] saltA = {0, 1, 6, 4, 8};
        byte[] saltB = {0, 7, 4, 8};
        SecretKeySpec keyA = null;
        SecretKeySpec keyB = null;

        try {
            keyA = KeyMaster.generateSecretKey(password, saltA);
            keyB = KeyMaster.generateSecretKey(password, saltB);
        } catch (Exception ignored) {
        }

        assertNotNull(keyA);
        assertNotNull(keyB);
        assertNotEquals(keyA, keyB);
    }

    @Test
    void asymmetricKeyPair() {
        KeyPair keyPair = null;
        try {
            keyPair = KeyMaster.generateKeyPair();
        } catch (Exception ignored) {
        }

        assertNotNull(keyPair);
    }

    @Test
    void asymmetricKeyPairNotEqual() {
        KeyPair keyPairA = null;
        KeyPair keyPairB = null;
        try {
            keyPairA = KeyMaster.generateKeyPair();
            keyPairB = KeyMaster.generateKeyPair();
        } catch (Exception ignored) {
        }

        assertNotEquals(keyPairA, keyPairB);
    }



    @Test
    void zeroPublicKeys() {

    }

    @Test
    void publicKeyOf() {
    }

    @Test
    void addPublicKey() {
    }

    @Test
    void testAddPublicKey() {
    }
}