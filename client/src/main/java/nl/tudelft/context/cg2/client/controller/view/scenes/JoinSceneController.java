package nl.tudelft.context.cg2.client.controller.view.scenes;

import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.view.SceneController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;
import nl.tudelft.context.cg2.client.view.scenes.JoinScene;

/**
 * The Lobby scene controller class.
 * Controls the lobby scene.
 */
public class JoinSceneController extends SceneController {

    private final JoinScene scene;
    private int selectedLobby;

    /**
     * The SceneController constructor.
     *
     * @param controller the controller class.
     * @param model the model class.
     * @param view       the view class.
     */
    public JoinSceneController(Controller controller, Model model, View view) {
        super(controller, model, view);
        this.scene = view.getJoinScene();
    }

    @Override
    protected void setupMouseListeners() {
        scene.getJoinButton().setOnMouseClicked(event -> {
            controller.joinGameCallback(scene.getPlayerNameField().getText(), selectedLobby);
        });
        scene.getLobbyEntries().forEach(lobby -> {
            lobby.setOnMouseClicked(event -> {
                this.selectedLobby = scene.getLobbyEntries().indexOf(lobby);
            });
        });
        scene.getBackButton().setOnMouseClicked(event -> {
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
