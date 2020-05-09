package nl.tudelft.context.cg2.client;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.tudelft.context.cg2.client.controller.MainMenuController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;

import java.io.IOException;

/**
 * JavaFX App.
 */
@SuppressFBWarnings(value = {"URF_UNREAD_FIELD", "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD" },
                    justification = "'controller' will be used very soon."
                            + "Don't know how to do this otherwise.")

public class App extends Application {

    //private Controller controller;
    private static View view;
    //private Model model;

    /**
     * Launches the javafx application.
     * @param stage the window displayed to the user.
     */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader mainMenuLoader = new FXMLLoader(
                getClass().getClassLoader().getResource("fxml/MainMenu.fxml"));
        stage.setScene(new Scene(mainMenuLoader.load()));
        MainMenuController mainMenuController = mainMenuLoader.getController();

        view = new View(stage);
        Model model = new Model(view);
        mainMenuController.init(model, view);
        model.loadData();

        view.loadScene("fxml/MainMenu.fxml");
        stage.show();
    }

    /**
     * Ran as the very last method when the application is shut down.
     */
    @Override
    public void stop() {

    }

    /**
     * The main method.
     * @param args arguments passed to the main method.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Getter for view field.
     * @return View field value.
     */
    public static View getView() {
        return view;
    }
}