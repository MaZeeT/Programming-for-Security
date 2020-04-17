package UI.GUI;

import UI.UI;
import javafx.scene.Scene;

public class ChatGUI implements UI {
    private ChatView view;
    private ChatController controller;

    public ChatGUI(int x, int y) {
        view = new ChatView(x, y);
        controller = new ChatController(view);
    }

    public Scene getScene() {
        return view.GetScene();
    }

}
