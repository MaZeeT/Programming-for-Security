package Client.GUI;

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
            view.textArea.appendText(view.textField.getText() + System.lineSeparator());
            view.textField.setText("");
            view.textField.requestFocus();
        };
    }

}
