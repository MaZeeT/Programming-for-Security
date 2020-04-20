package Encryption;

import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

import static org.junit.jupiter.api.Assertions.*;

class KeyMasterTest {

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
    void addPublicKey() {
        KeyMaster keyMaster = new KeyMaster();
        try {
            keyMaster.addPublicKey(
                    "firstKey",
                    (RSAPublicKey) KeyMaster.generateKeyPair().getPublic());
        } catch (Exception e) {
            e.printStackTrace();
        }

        int expected = 0;
        int numberOfKeys = keyMaster.numberOfPublicKeys();

        assertNotEquals(expected, numberOfKeys);
    }

    @Test
    void zeroPublicKeys() {
        KeyMaster keyMaster = new KeyMaster();

        int expected = 0;
        int numberOfKeys = keyMaster.numberOfPublicKeys();

        assertEquals(expected, numberOfKeys);
    }

    @Test
    void onePublicKeys() {
        KeyMaster keyMaster = new KeyMaster();
        RSAPublicKey publicKey = null;
        try {
            publicKey = (RSAPublicKey) KeyMaster.generateKeyPair().getPublic();
        } catch (Exception e) {
            e.printStackTrace();
        }
        keyMaster.addPublicKey("firstKey", publicKey);

        int expected = 1;
        int numberOfKeys = keyMaster.numberOfPublicKeys();

        assertEquals(expected, numberOfKeys);
    }

    @Test
    void twoPublicKeys() {
        KeyMaster keyMaster = new KeyMaster();
        RSAPublicKey publicKeyA = null;
        RSAPublicKey publicKeyB = null;
        try {
            publicKeyA = (RSAPublicKey) KeyMaster.generateKeyPair().getPublic();
            publicKeyB = (RSAPublicKey) KeyMaster.generateKeyPair().getPublic();
        } catch (Exception e) {
            e.printStackTrace();
        }
        keyMaster.addPublicKey("keyA", publicKeyA);
        keyMaster.addPublicKey("keyB", publicKeyB);

        int expected = 2;
        int numberOfKeys = keyMaster.numberOfPublicKeys();

        assertEquals(expected, numberOfKeys);
    }

    @Test
    void publicKeyOf() {
        KeyMaster keyMaster = new KeyMaster();
        RSAPublicKey publicKeyA = null;
        RSAPublicKey publicKeyB = null;
        try {
            publicKeyA = (RSAPublicKey) KeyMaster.generateKeyPair().getPublic();
            publicKeyB = (RSAPublicKey) KeyMaster.generateKeyPair().getPublic();
        } catch (Exception e) {
            e.printStackTrace();
        }
        keyMaster.addPublicKey("keyA", publicKeyA);
        keyMaster.addPublicKey("keyB", publicKeyB);

        assertEquals(publicKeyB, keyMaster.publicKeyOf("keyB"));
    }

    @Test
    void GetKeys() {
        KeyMaster keyMaster = null;
        KeyPair keyPair = null;
        SecretKeySpec secretKey = null;

        try {
            char[] password = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd', '6'};
            byte[] salt = {0, 6, 4, 8};
            secretKey = KeyMaster.generateSecretKey(password,salt);
            keyPair = KeyMaster.generateKeyPair();
            keyMaster = new KeyMaster(secretKey,keyPair);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(keyPair,keyMaster.keyPair());
        assertEquals(secretKey,keyMaster.secretKey());
    }

}