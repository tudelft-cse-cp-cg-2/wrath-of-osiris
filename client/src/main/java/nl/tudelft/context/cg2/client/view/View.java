package nl.tudelft.context.cg2.client.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class View {

    private Stage stage;
    private Scene scene;

    public View(Stage stage) {
        this.stage = stage;
        stage.setTitle("Hole in the wall");
        stage.setResizable(true);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setX(0.D);
        stage.setY(0.D);

        this.scene = new Scene(new StackPane(new Text("Hello world")));
    }

    public void loadScene() {
        stage.setScene(scene);
    }

    public void updateScene(double t, double dt) {
    }
}
