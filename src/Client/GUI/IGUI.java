package Client.GUI;

import Client.ChatClient;
import Network.Message;
import javafx.scene.Scene;

import java.util.List;

public interface IGUI {

    Scene getScene();
    void setClient(ChatClient client);
    void setChat(List<Message> messageHistory);
}
