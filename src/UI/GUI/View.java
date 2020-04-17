package UI.GUI;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public abstract class View {
    private int x;
    private int y;
    BorderPane pane;

    public View(int x, int y) {
        this.x = x;
        this.y = y;
        pane = new BorderPane();
    }

    public Scene GetScene() {
        return new Scene(pane, x, y);
    }

}
