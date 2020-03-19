package Client;

import Client.GUI.ChatGUI;
import Client.GUI.IGUI;
import Network.ChatConnection;
import Network.Message;
import Server.ChatServer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String ip = "127.0.0.1";
        int port = 3000;
        //launchGUI(primaryStage,ip,port);
        launchConsole(ip,port);

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void launchGUI(Stage primaryStage, String ip, int port) throws IOException {
        //launch gui + window

        IGUI gui = new ChatGUI(800,600);
        gui.setConnection(new ChatConnection(new Socket(ip, port)));
        primaryStage.setTitle("ChatCrypt");
        primaryStage.setScene(gui.getScene());
        primaryStage.show();

        //test population of gui textarea
        String[] messages = {"lol","more lol","text string"};
        gui.setChat(messages);
    }

    private void launchConsole(String ip, int port) throws IOException {
        System.out.println("Hello world, I am mr. client");
        ChatClient client = new ChatClient(ip,port);

        // let you type from console when startSendingThread is called
        client.startSendingThread();


    }

}
