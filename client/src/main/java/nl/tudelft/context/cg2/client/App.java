package nl.tudelft.context.cg2.client;

import javafx.application.Application;
import javafx.stage.Stage;
import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;

/**
 * JavaFX App
 */
public class App extends Application {

    private Controller controller;
    private View view;
    private Model model;

    @Override
    public void start(Stage stage) {
        this.model = new Model();
        model.loadData();

        this.view = new View(stage);
        view.loadScene();

        this.controller = new Controller(model, view);

        stage.show();
    }

    @Override
    public void stop() {

    }

    public static void main(String[] args) {
        launch();
    }

}