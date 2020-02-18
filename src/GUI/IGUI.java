package GUI;

import javafx.scene.Scene;

public interface IGUI {

    Scene getScene();
    void setChat(String[] messages);
}
