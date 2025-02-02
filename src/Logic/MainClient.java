package Logic;

import Encryption.KeyMaster;
import UI.GUI.ChatGUI;
import UI.TerminalUI;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

public class MainClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    //password and salt is used to generate the secretKey used for the chat encryption
    char[] password = "HardcodedPasswordIsBad".toCharArray();
    byte[] salt = {0, 1, 1, 1, 2, 0, 6, 3};

    @Override
    public void start(Stage primaryStage) throws Exception {
        String ip = "127.0.0.1";
        int port = 3000;
        String username = "ClientTerminal";

        //the terminal or GUI ui can be commented out or in depending on which ui is wanted, default both is launched
        ClientLogic client = launchClient(ip, port, username);
        TerminalUI ui = launchTerminal(username);
        ChatGUI gui = launchGUI(primaryStage, username);
    }

    private ChatGUI launchGUI(Stage primaryStage, String username) {
        ChatGUI gui = new ChatGUI(username, 800, 600);
        primaryStage.setTitle("ChatCrypt");
        primaryStage.setScene(gui.getScene());
        primaryStage.show();
        return gui;
    }

    private TerminalUI launchTerminal(String username) {
        return new TerminalUI(username);
    }

    private ClientLogic launchClient(String ip, int port, String username) throws Exception {
        //generate keys and store them in the KeyMaster class
        SecretKeySpec secretKey = KeyMaster.generateSecretKey(password, salt);
        KeyPair keyPair = KeyMaster.generateKeyPair();
        KeyMaster keyMaster = new KeyMaster(secretKey, keyPair);

        //adds own public key to a map of <username,publicKey>
        //(all trusted usernames should have a public key in this map)
        RSAPublicKey publicKey = (RSAPublicKey) keyMaster.keyPair().getPublic();
        keyMaster.addPublicKey(username, publicKey);

        return new ClientLogic(ip, port, keyMaster);
    }

}
