package Client.GUI;

import Network.ChatConnection;
import javafx.scene.Scene;

public interface IGUI {

    Scene getScene();
    void setConnection(ChatConnection connection);
    void setChat(String[] messages);
}
