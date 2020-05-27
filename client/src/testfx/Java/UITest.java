import javafx.stage.Stage;
import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;
import org.testfx.framework.junit5.ApplicationTest;

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
}
