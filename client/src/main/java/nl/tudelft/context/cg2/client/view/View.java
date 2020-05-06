package nl.tudelft.context.cg2.client.view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * The view class.
 */
public class View {

    private static Stage stage;
    private static Scene scene;
    public static FXMLLoader loader;

    /**
     * The view constructor.
     * @param stage the javafx window being displayed to the user.
     */
    public View(Stage stage) throws IOException, URISyntaxException {
        this.stage = stage;
        this.loader = new FXMLLoader();
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
        Parent root = loader.load(View.class.getClassLoader().getResource("fxml/MainMenu.fxml"));
        stage.setScene(new Scene(root));
    }

    /**
     * Loads a javafx scene into the window.
     */
    public static void loadScene(Scene scene) throws IOException {
        stage.setScene(scene);
    }
}
