
import javafx.scene.Parent;
import javafx.stage.Stage;
import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;

import static org.testfx.assertions.api.Assertions.assertThat;

public class UITest extends ApplicationTest {
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

//    @Test
//    public void joinGameTest() {
//        clickOn("Join Game");
//        assertThat(view.getWindow().getShownScene()).isEqualTo(view.getJoinScene());
//        write("TestName");
//        clickOn("Join Lobby");
//        assertThat(view.getWindow().getShownScene()).isEqualTo(view.getLobbyScene());
//        assertThat(view.getLobbyScene().getWaitMessage().isVisible()).isTrue();
//        ArrayList<String> names = new ArrayList<>();
//        view.getLobbyScene().getPlayerEntries().forEach(label -> {
//            names.add(label.getText());
//        });
//        assertThat(names.contains("TestName")).isTrue();
//        clickOn("Leave");
//        assertThat(view.getWindow().getShownScene()).isEqualTo(view.getMenuScene());
//    }


}
