package nl.tudelft.context.cg2.client.controller.view.scenes;

import com.github.sarxos.webcam.Webcam;
import javafx.scene.input.MouseEvent;
import nl.tudelft.context.cg2.client.Settings;
import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.view.SceneController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;
import nl.tudelft.context.cg2.client.view.elements.buttons.SimpleButton;
import nl.tudelft.context.cg2.client.view.scenes.SettingsScene;

import java.util.ArrayList;

/**
 * The Create Game scene controller class.
 * Controls the game creation scene.
 */
public class SettingsSceneController extends SceneController {

    private final SettingsScene scene;

    /**
     * The SceneController constructor.
     * @param controller the controller class.
     * @param model the model class.
     * @param view       the view class.
     */
    public SettingsSceneController(Controller controller, Model model, View view) {
        super(controller, model, view);
        scene = view.getSettingsScene();
    }

    @Override
    protected void setupMouseListeners() {
        scene.getLeaveButton().setOnMouseClicked(event -> leaveButtonClicked());
    }

    @Override
    protected void setupKeyboardListeners() {

    }

    @Override
    protected void setupEventListeners() {

    }

    /**
     * Detects the cameras that are available on the machine.
     */
    public void detectCameras() {
        scene.setOptions(new ArrayList<>());
        Webcam.getWebcams().forEach(webcam -> {
            SimpleButton button = new SimpleButton(webcam.getName());
            button.setSize(1000, 50);
            button.setOnMouseClicked(this::selectCamera);
            scene.getOptions().add(button);
        });
        scene.reDraw();
    }

    /**
     * Select a camera.
     * @param event the event.
     */
    public void selectCamera(MouseEvent event) {
        SimpleButton btn = (SimpleButton) event.getSource();
        Settings.setWebcamName(btn.getText());
        getScene().reDraw();
    }

    /**
     * Getter for the scene.
     * @return the scene.
     */
    public SettingsScene getScene() {
        return scene;
    }

    /**
     * Callback for the Leave button listener.
     * Returns to the previous menu.
     */
    private void leaveButtonClicked() {
        view.getMenuScene().show();
    }
}
