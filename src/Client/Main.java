package Client;

import Client.GUI.ChatGUI;
import Client.GUI.IGUI;
import Server.ChatServer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String ip = "127.0.0.1";
        int port = 3000;
        //launchGUI(primaryStage);
        launchConsole(ip,port);
        //BaseChatConnection chatConnection = new ClientChatConnection(3000);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void launchGUI(Stage primaryStage){
        //launch gui + window
        IGUI gui = new ChatGUI(800,600);
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

    }

}
