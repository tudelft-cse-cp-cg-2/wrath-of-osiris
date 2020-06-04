package nl.tudelft.context.cg2.client.controller.view.scenes;

import javafx.scene.input.MouseEvent;
import nl.tudelft.context.cg2.client.Settings;
import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.view.SceneController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;
import nl.tudelft.context.cg2.client.view.elements.buttons.SimpleButton;
import nl.tudelft.context.cg2.client.view.scenes.SettingsScene;
import org.opencv.videoio.VideoCapture;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

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
        nu.pattern.OpenCV.loadLocally();
        scene.setOptions(new ArrayList<>());
        VideoCapture videoCapture = new VideoCapture();
        for (int i = 0; i < 10; i++) {
            videoCapture.open(i);
            if (videoCapture.isOpened()) {
                SimpleButton button = new SimpleButton("Camera index: " + i);
                button.setSize(220, 50);
                button.setOnMouseClicked(this::selectCamera);
                scene.getOptions().add(button);
            }
            videoCapture.release();
        }
        scene.reDraw();
    }

    /**
     * Select a camera.
     * @param event the event.
     */
    public void selectCamera(MouseEvent event) {
        SimpleButton btn = (SimpleButton) event.getSource();
        Settings.setCameraIndex(getIndexFromButton(btn));
        getScene().reDraw();
    }

    /**
     * Get the index from a button.
     * @param simpleButton the simpleButton.
     * @return the index.
     */
    private int getIndexFromButton(SimpleButton simpleButton) {
        String[] parts = simpleButton.getText().split(" ");
        return parseInt(parts[parts.length - 1]);
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
