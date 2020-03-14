package Client.GUI;

import Network.ChatConnection;
import Network.Message;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;

public class ChatController {
    ChatView view;
    ChatConnection connection = null;

    public ChatController(ChatView view) {
        this.view = view;
        view.textField.setOnAction(submitEvent());
        view.submitButton.setOnAction(submitEvent());
    }

    public void setConnection(ChatConnection connection){
        this.connection = connection;
    }

    private EventHandler<ActionEvent> submitEvent() {
        return actionEvent -> {
            String username = view.username.getText();
            String input = view.textField.getText();
            Message message = new Message(username, input);
            try{
                connection.send(message);
            }catch (IOException e){
                System.out.println(e.toString());
            }
            view.textArea.appendText(input);
            view.textField.setText("");
            view.textField.requestFocus();

        };
    }

}
