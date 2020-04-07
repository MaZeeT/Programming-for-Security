package Logic;

import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

public class KeyMaster {
    SecretKeySpec key;  //symmetric key
    KeyPair keyPair; // my private and public key
    Map<String, RSAPublicKey> keyMap; //map between username and public key.

    public KeyMaster(){
        keyMap = new HashMap<>();
    }


    public RSAPublicKey publicKeyOf(String username){
        return keyMap.get(username);
    }

    //todo make test of this keyMaster class
    //todo refactor some key generation into a utility class
    //todo rename some parts of the program into a component package or Encryption Package?
    //todo when done with components (see Yed diagram) consider to rework the chatClient/Server.
    //todo are custom eventHandling still useful or doesn't it solve the problem it is intended for.

}
