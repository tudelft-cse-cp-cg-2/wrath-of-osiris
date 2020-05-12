package nl.tudelft.context.cg2.client.controller.view.scenes;

import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.view.SceneController;
import nl.tudelft.context.cg2.client.view.View;
import nl.tudelft.context.cg2.client.view.scenes.LobbyScene;

import java.util.ArrayList;

/**
 * The Lobby scene controller class.
 * Controls the lobby scene.
 */
public class LobbySceneController extends SceneController {

    private final LobbyScene scene;

    /**
     * The SceneController constructor.
     *
     * @param controller the controller class.
     * @param view       the view class.
     */
    public LobbySceneController(Controller controller, View view) {
        super(controller, view);
        this.scene = view.getLobbyScene();
    }

    @Override
    protected void setupMouseListeners() {
        scene.getStartButton().setOnMouseClicked(event -> {
            System.out.println("Clicked");
        });
        scene.getLeaveButton().setOnMouseClicked(event -> {
            controller.leaveLobbyCallback();
            scene.setPlayerNames(new ArrayList<>());
            view.getMenuScene().show();
        });
    }

    @Override
    protected void setupKeyboardListeners() {

    }

    @Override
    protected void setupEventListeners() {

    }
}
