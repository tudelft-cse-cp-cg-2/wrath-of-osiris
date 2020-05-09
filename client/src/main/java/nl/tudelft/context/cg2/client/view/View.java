package nl.tudelft.context.cg2.client.view;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * The view class.
 */
@SuppressFBWarnings()
@SuppressWarnings("HideUtilityClassConstructor")
public class View {

    private Stage stage;
    private Scene scene;
    private FXMLLoader loader = new FXMLLoader();

    /**
     * The view constructor.
     * @param stage The javafx window being displayed to the user.
     * @throws IOException Whether FXML resource is found.
     */
    public View(Stage stage) throws IOException {
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
    }

    /**
     * Loads a javafx scene into the window.
     * @param fxml FXML file to load.
     */
    public void loadScene(String fxml) {
        URL url = View.class.getClassLoader().getResource(fxml);
        Scene scene;
        try {
            scene = new Scene(loader.load(url));
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("FXML was not found: " + fxml);
            e.printStackTrace();
            stage.close();
            Platform.exit();
            System.exit(0);
        }
    }
}
