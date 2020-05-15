package nl.tudelft.context.cg2.client;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javafx.application.Application;
import javafx.stage.Stage;

import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;

/**
 * JavaFX App.
 */
public class App extends Application {

    private Model model;
    private View view;
    private Controller controller;

    /**
     * Launches the javafx application.
     *
     * @param stage the window displayed to the user.
     */
    @Override
    public void start(Stage stage) {
        this.model = new Model();
        model.load();

        this.view = new View(stage, model);
        view.getMenuScene().show();

        this.controller = new Controller(model, view);
        controller.getGameTimer().start();

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
     *
     * @param args arguments passed to the main method.
     */
    public static void main(String[] args) {
        launch(args);
    }
}