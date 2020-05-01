package nl.tudelft.context.cg2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.tudelft.context.cg2.controller.Controller;
import nl.tudelft.context.cg2.model.Model;
import nl.tudelft.context.cg2.view.View;

import java.io.IOException;

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
        controller.getGraphicsTimer().start();

        stage.show();
    }

    @Override
    public void stop() {

    }

    public static void main(String[] args) {
        launch();
    }

}