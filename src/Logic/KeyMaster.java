package Logic;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

public class KeyMaster {
    SecretKeySpec key;  //symmetric key
    KeyPair keyPair; // my private and public key
    Map<String, RSAPublicKey> keyMap; //map between username and public key.

    public KeyMaster(SecretKeySpec symmetricKey, KeyPair asymmetricKeyPair){
        this.key = symmetricKey;
        this.keyPair = asymmetricKeyPair;
        this.keyMap = new HashMap<>();
    }

    public SecretKeySpec symmetricKey(){
        return key;
    }

    public KeyPair asymmetricKeyPair(){
        return keyPair;
    }

    public RSAPublicKey publicKeyOf(String username){
        return keyMap.get(username);
    }

    public void addPublicKey(String username, RSAPublicKey publicKey){
        keyMap.put(username,publicKey);
    }

    public void addPublicKey(Map<String,RSAPublicKey> publicKeyMap){
        keyMap.putAll(publicKeyMap);
    }

    public static SecretKeySpec generateSecretKey(char[] password, byte[] salt){
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WITHHMACSHA256", "BC");
            byte[] keyBytes = factory.generateSecret(
                    new PBEKeySpec(password, salt, 10, 128)
            ).getEncoded(); //todo change iterationCount
            return new SecretKeySpec(keyBytes, "AES");

        } catch (Exception e) {
            return null;
        }
    }

    public static KeyPair generateKeyPair() throws NoSuchProviderException, NoSuchAlgorithmException {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");

            //initialize the generator with a key size (could be a random size)
            generator.initialize(2048);

            //generate a key pair
            return generator.generateKeyPair();
    }

    //todo make test of this keyMaster class
    //todo refactor some key generation into a utility class
    //todo rename some parts of the program into a component package or Encryption Package?
    //todo when done with components (see Yed diagram) consider to rework the chatClient/Server.
    //todo are custom eventHandling still useful or doesn't it solve the problem it is intended for.

}
