package Logic;

import ChatEvents.EventNotifier;
import Encryption.KeyMaster;
import Network.Message;
import UI.GUI.ChatGUI;
import UI.TerminalUI;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

public class MainTestClient extends Application {

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
        String username = "TestClient";

        //the terminal or GUI ui can be commented out or in depending on which ui is wanted, default both is launched
        ClientLogic client = launchClient(ip, port, username);
        TerminalUI ui = launchTerminal(username);
        ChatGUI gui = launchGUI(primaryStage, username);

        sleep(500);
        runTests(username);
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

    private void runTests(String username) {
        say(username, "This is the predefined tests, I will show how this program works");
        say(username, "Now I will say -TESTING CHAT- 3 times");
        say(username, "-TESTING CHAT-");
        say(username, "-TESTING CHAT-");
        say(username, "-TESTING CHAT-");
        say(username, "Now I will say -USER TESTING- 3 times where the 2nd time is an invalid user");
        say(username, "and should not show up in the client but should be able to be seen on the server");
        say(username, "-USER TESTING-");
        say("Mr. hacker", "-USER TESTING-");
        say(username, "-USER TESTING-");
        say(username, "All valid messages should be shown on the client and a encrypted version on the server");
        say(username, "for invalid messages it will not be shown on the client but a encrypted version can still be seen on the server");
    }

    private void say(String username, String chat) {
        Message message = new Message(username, chat);
        EventNotifier.messageSent.publishEvent(message);
        sleep(200);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
        }
    }

}
