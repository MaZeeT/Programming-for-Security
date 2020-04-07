package Logic;

import Network.Message;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class SignatureSigner {

    public static boolean Sign(Message message, RSAPrivateKey privateKey) {
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

    public static boolean Verify(Message message, RSAPublicKey publicKey) {
        try {
            byte[] inputMessage = message.toString().getBytes();
            Signature signature = Signature.getInstance("SHA256withRSA", "BC");
            signature.initVerify(publicKey);
            signature.update(inputMessage);
            return signature.verify(message.getSignature());
        } catch (Exception e) {
            return false;
        }
    }

}
