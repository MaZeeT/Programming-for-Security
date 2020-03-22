package UI.GUI;

import ChatEvents.EventNotifier;
import Client.ChatClient;
import Network.ChatConnection;
import Network.Message;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ChatController {
    ChatView view;
    ChatConnection connection = null;
    ChatClient client = null;

    public ChatController(ChatView view) {
        this.view = view;
        view.textField.setOnAction(submitEvent());
        view.submitButton.setOnAction(submitEvent());
    }

    public void setConnection(ChatClient client){
        this.client = client;
    }

    private EventHandler<ActionEvent> submitEvent() {
        return actionEvent -> {
            String username = view.username.getText();
            String input = view.textField.getText();
            Message message = new Message(username, input);
            EventNotifier.messageSent.publishEvent(message); //todo event publish
            try{
              //  client.sendMessage(message); //todo fix this
            }catch (Exception e){
                System.out.println(e.toString());
            }
          //  view.textArea.appendText(input);
            view.textField.setText("");
            view.textField.requestFocus();
        };
    }

}
