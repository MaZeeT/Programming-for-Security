package Logic;

import Network.Message;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class SignatureSigner {

    public static boolean sign(Message message, KeyPair keyPair) {
        return sign(message, (RSAPrivateKey) keyPair.getPrivate());
    }

    public static boolean sign(Message message, RSAPrivateKey privateKey) {
        try {
            byte[] inputMessage = message.toString().getBytes();
            Signature signature = Signature.getInstance("SHA256withRSA", "BC");
            signature.initSign(privateKey);
            signature.update(inputMessage);
            return message.signMessage(signature.sign());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean verify(Message message, KeyPair keyPair) {
        return verify(message, (RSAPublicKey) keyPair.getPublic());
    }

    public static boolean verify(Message message, RSAPublicKey publicKey) {
        try {
            byte[] inputMessage = message.toString().getBytes();
            Signature signature = Signature.getInstance("SHA256withRSA", "BC");
            signature.initVerify(publicKey);
            signature.update(inputMessage);
            return signature.verify(message.signature());
        } catch (Exception e) {
            return false;
        }
    }

}
