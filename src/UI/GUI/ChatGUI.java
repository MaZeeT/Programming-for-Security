package UI.GUI;

import javafx.scene.Scene;

public class ChatGUI {
    private ChatView view;
    private ChatController controller;

    public ChatGUI(String username, int x, int y) {
        view = new ChatView(x, y);
        view.username.setText(username);
        controller = new ChatController(view);
    }

    public Scene getScene() {
        return view.GetScene();
    }

}
