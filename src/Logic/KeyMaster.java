package Logic;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

public class KeyMaster {
    SecretKeySpec key = null;  //symmetric key
    KeyPair keyPair = null; //private and public key
    Map<String, RSAPublicKey> keyMap; //map with key:username and value:public key.

    public KeyMaster() {
        this.keyMap = new HashMap<>();
    }

    public KeyMaster(SecretKeySpec secretKey, KeyPair keyPair) {
        this.key = secretKey;
        this.keyPair = keyPair;
        this.keyMap = new HashMap<>();
    }

    public SecretKeySpec symmetricKey() {
        return key;
    }

    public KeyPair asymmetricKeyPair() {
        return keyPair;
    }

    public RSAPublicKey publicKeyOf(String username) {
        return keyMap.get(username);
    }

    public void addPublicKey(String username, RSAPublicKey publicKey) {
        keyMap.put(username, publicKey);
    }

    public int numberOfPublicKeys() {
        return keyMap.size();
    }

    public static SecretKeySpec generateSecretKey(char[] password, byte[] salt) throws InvalidKeySpecException, NoSuchProviderException, NoSuchAlgorithmException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WITHHMACSHA256", "BC");
        byte[] keyBytes = factory.generateSecret(
                new PBEKeySpec(password, salt, 1000, 128)
        ).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }

    public static KeyPair generateKeyPair() throws NoSuchProviderException, NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
        generator.initialize(2048);
        return generator.generateKeyPair();
    }

}
