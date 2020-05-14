package nl.tudelft.context.cg2.client;

import javafx.scene.Parent;
import javafx.stage.Stage;
import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.assertions.api.Assertions.assertThat;

public class MenuTest extends ApplicationTest {
    private Controller controller;
    private View view;
    private Model model;


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


    @Test
    public void should_contain_buttons() {
        clickOn("Join Game");
        assertThat(view.getWindow().getShownScene()).isEqualTo(view.getJoinScene());
    }
}
