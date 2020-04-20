package Encryption;

import Network.*;

import javax.crypto.Cipher;
import javax.crypto.spec.*;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class AESEncryption {
    SecretKeySpec secretKey;
    Cipher cipher;

    public AESEncryption(SecretKeySpec secretKey) {
        this.secretKey = secretKey;
        try {
            this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CipherMessage Encrypt(Message plainMessage) {
        try {
            byte[] iv = RandomIV();
            byte[] message = plainMessage.message().getBytes(StandardCharsets.UTF_8);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            message = cipher.doFinal(message);
            return new CipherMessage(plainMessage, message, iv);
        } catch (Exception ignored) {
            return null;
        }
    }

    public Message Decrypt(CipherMessage cipherMessage) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(cipherMessage.iv()));
            byte[] plainText = cipher.doFinal(cipherMessage.cipherText());
            return new Message(cipherMessage, plainText);
        } catch (Exception ignored) {
            return null;
        }
    }

    public byte[] RandomIV() throws NoSuchProviderException, NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("DEFAULT", "BC");
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        return iv;
    }

}
