package GUI;

import javafx.scene.Scene;

public class ChatGUI implements IGUI {
    ChatView view;
    ChatController controller;

    public ChatGUI(int x, int y) {
        view = new ChatView(x, y);
        controller = new ChatController(view);
    }

    @Override
    public Scene getScene() {
        return view.GetScene();
    }

    @Override
    public void setChat(String[] messages) {
        for (String message : messages) {
            view.textArea.appendText(message + System.lineSeparator());
        }
    }
}
