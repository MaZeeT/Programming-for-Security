package UI.GUI;

import ChatEvents.*;
import Network.Message;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class ChatView extends View implements EventSubscriber<Message> {

    public ChatView(int x, int y) {
        super(x, y);
        configPane();
        EventNotifier.messageReceived.subscribe(this);
    }

    //textArea to show the chat
    protected TextArea textArea = new TextArea("");

    //field to enter chat text
    protected TextField username = new TextField("GUI");
    protected TextField textField = new TextField("");

    //button to submit a message to the system
    protected Button submitButton = new Button("Enter");

    @Override
    public void eventUpdate(Message message, String eventName) {
        String text = message + System.lineSeparator();
        textArea.appendText(text);
    }

    private void configPane() {
        int width = 5000;
        textArea.setEditable(false);

        username.setMinWidth(100);
        username.setMaxWidth(150);
        textField.setPrefWidth(width);
        textField.setMaxWidth(width);

        submitButton.setMinWidth(80);

        //Box to align the bottom part of the pane
        HBox bottomBox = new HBox();
        bottomBox.getChildren().add(username);
        bottomBox.getChildren().add(textField);
        bottomBox.getChildren().add(submitButton);

        //add the entities to the pane
        pane.setBottom(bottomBox);
        pane.setCenter(textArea);

    }

}
