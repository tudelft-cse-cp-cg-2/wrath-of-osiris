package nl.tudelft.context.cg2.client.controller.view.scenes;

import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.view.SceneController;
import nl.tudelft.context.cg2.client.view.View;
import nl.tudelft.context.cg2.client.view.scenes.CreateGameScene;

/**
 * The Create Game scene controller class.
 * Controls the game creation scene.
 */
public class CreateGameSceneController extends SceneController {

    private final CreateGameScene scene;

    /**
     * The SceneController constructor.
     *
     * @param controller the controller class.
     * @param view       the view class.
     */
    public CreateGameSceneController(Controller controller, View view) {
        super(controller, view);
        this.scene = view.getCreateGameScene();
    }

    @Override
    protected void setupMouseListeners() {
        scene.getCreateGameButton().setOnMouseClicked(event -> {
            view.getLobbyScene().show();
        });
        scene.getLeaveButton().setOnMouseClicked(event -> {
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
