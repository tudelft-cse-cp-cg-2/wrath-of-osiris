package nl.tudelft.context.cg2.client.view;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The view class.
 */
@SuppressFBWarnings()
@SuppressWarnings("HideUtilityClassConstructor")
public class View {

    private static Stage stage;
    private static Scene scene;
    private static FXMLLoader loader;

    /**
     * The view constructor.
     * @param stage The javafx window being displayed to the user.
     * @throws IOException Whether FXML resource is found.
     */
    public View(Stage stage) throws IOException {
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
     * @param scene The scene to load.
     */
    public static void loadScene(Scene scene) {
        stage.setScene(scene);
    }
}
