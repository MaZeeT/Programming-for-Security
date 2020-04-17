package Client;

import Logic.KeyMaster;
import UI.GUI.ChatGUI;
import UI.GUI.IGUI;
import UI.TerminalUI;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String ip = "127.0.0.1";
        int port = 3000;
        char[] password = "HardcodedPasswordIsBad".toCharArray();
        byte[] salt = {0, 1, 1, 1, 2, 0, 6, 3};
        SecretKeySpec secretKey = KeyMaster.generateSecretKey(password, salt);
        KeyPair keyPair = KeyMaster.generateKeyPair();
        KeyMaster keyMaster = new KeyMaster(secretKey, keyPair);
        //launchGUI(primaryStage, ip, port, key);
        launchConsole(ip, port, keyMaster);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void launchGUI(Stage primaryStage, String ip, int port, KeyMaster keyMaster) throws Exception {
        System.out.println("Hello world, I am mr. client");
        String username = "ClientGUI";
        RSAPublicKey publicKey = (RSAPublicKey) keyMaster.asymmetricKeyPair().getPublic();
        keyMaster.addPublicKey(username, publicKey);

        //launch gui + window
        ChatClient client = new ChatClient(ip, port, keyMaster);

        IGUI gui = new ChatGUI(800, 600);
        primaryStage.setTitle("ChatCrypt");
        primaryStage.setScene(gui.getScene());
        primaryStage.show();
    }

    private void launchConsole(String ip, int port, KeyMaster keyMaster) throws Exception {
        String username = "ClientTerminal";
        TerminalUI ui = new TerminalUI(username);

        RSAPublicKey publicKey = (RSAPublicKey) keyMaster.asymmetricKeyPair().getPublic();
        keyMaster.addPublicKey(username, publicKey);
        ChatClient client = new ChatClient(ip, port, keyMaster);
    }

}
