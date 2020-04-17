package UI.GUI;

import ChatEvents.EventNotifier;
import Network.Message;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ChatController {
    ChatView view;

    public ChatController(ChatView view) {
        this.view = view;
        view.textField.setOnAction(submitEvent());
        view.submitButton.setOnAction(submitEvent());
    }

    private EventHandler<ActionEvent> submitEvent() {
        return actionEvent -> {
            //generate and publish a new message
            String username = view.username.getText();
            String input = view.textField.getText();
            Message message = new Message(username, input);
            EventNotifier.messageSent.publishEvent(message); //todo event publish

            //clears and focus input textField
            view.textField.setText("");
            view.textField.requestFocus();
        };
    }

}
