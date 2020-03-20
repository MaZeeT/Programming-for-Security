package Client.GUI;

import Client.ChatClient;
import Network.Message;
import javafx.scene.Scene;

import java.util.List;

public class ChatGUI implements IGUI {
    ChatView view;
    ChatController controller;

    public ChatGUI(int x, int y) {
        view = new ChatView(x, y);
        controller = new ChatController(view);
    }

    @Override
    public void setClient(ChatClient client) {
        controller.setConnection(client);
    }

    @Override
    public Scene getScene() {
        return view.GetScene();
    }

    @Override
    public void setChat(List<Message> messageHistory) {
        new Thread(() -> {
            int i = 0;
            while (true) {
                if (i < messageHistory.size()) {
                    view.textArea.appendText(messageHistory.get(i).toString() + System.lineSeparator());
                    i++;
                }
                System.out.println(i); //todo figure out why this is needed to display message in gui
            }
        }).start();
    }
}
