package sample;

import GUI.ChatGUI;
import GUI.IGUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        IGUI gui = new ChatGUI(800,600);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(gui.getScene());
        primaryStage.show();

        String[] messages = {"lol","more lol","text string"};
        gui.setChat(messages);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
