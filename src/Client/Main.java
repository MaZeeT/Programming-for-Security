package Client;

import Logic.KeyMaster;
import UI.GUI.ChatGUI;
import UI.TerminalUI;
import UI.UI;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String ip = "127.0.0.1";
        int port = 3000;

        char[] password = "HardcodedPasswordIsBad".toCharArray();
        byte[] salt = {0, 1, 1, 1, 2, 0, 6, 3};

        SecretKeySpec secretKey = KeyMaster.generateSecretKey(password, salt);
        KeyPair keyPair = KeyMaster.generateKeyPair();
        KeyMaster keyMaster = new KeyMaster(secretKey, keyPair);

        String username = "ClientTerminal";
        ChatClient client = launchClient(ip, port, keyMaster, username);
        UI ui = launchTerminal(username);
        UI gui = launchGUI(primaryStage, username);
    }

    private UI launchGUI(Stage primaryStage, String username) {
        ChatGUI gui = new ChatGUI(username,800, 600);
        primaryStage.setTitle("ChatCrypt");
        primaryStage.setScene(gui.getScene());
        primaryStage.show();
        return (UI) gui;
    }

    private UI launchTerminal(String username) {
        return new TerminalUI(username);
    }

    private ChatClient launchClient(String ip, int port, KeyMaster keyMaster, String username) throws IOException {
        RSAPublicKey publicKey = (RSAPublicKey) keyMaster.asymmetricKeyPair().getPublic();
        keyMaster.addPublicKey(username, publicKey);
        return new ChatClient(ip, port, keyMaster);
    }

}
