package nl.tudelft.context.cg2.client.view;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import nl.tudelft.context.cg2.client.view.scenes.CreateGameScene;
import nl.tudelft.context.cg2.client.view.scenes.LobbyScene;
import nl.tudelft.context.cg2.client.view.scenes.MenuScene;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The view class.
 */
public class View {

    private final Window window;

    private final ArrayList<BaseScene> scenes;
    private final MenuScene menuScene;
    private final CreateGameScene createGameScene;
    private final LobbyScene lobbyScene;

    /**
     * The view constructor.
     * @param stage the javafx window being displayed to the user.
     */
    public View(final Stage stage) {
        this.window = new Window(stage);
        this.menuScene = new MenuScene(window, new StackPane());
        this.createGameScene = new CreateGameScene(window, new StackPane());
        this.lobbyScene = new LobbyScene(window, new StackPane());
        this.scenes = new ArrayList<>(Arrays.asList(
                menuScene,
                createGameScene,
                lobbyScene
        ));

        scenes.forEach(BaseScene::draw);
        window.resizedProperty().addListener((obj, oldV, newV) -> onResized(newV));
    }

    /**
     * Updates everything that runs on the graphics timer.
     * @param t the passed time in s since timer initialization.
     * @param dt the passed time in s since the last update.
     */
    public void update(double t, double dt) {
        window.getShownScene().animate();
    }

    /**
     * Handles the windows resize action.
     * @param resized was resized or not.
     */
    private void onResized(boolean resized) {
        if (resized) {
            window.getShownScene().onResized();
        }
    }

    /**
     * The window getter.
     * @return the window.
     */
    public Window getWindow() {
        return window;
    }

    /**
     * The menu scene getter.
     * @return the menu scene.
     */
    public MenuScene getMenuScene() {
        return menuScene;
    }

    /**
     * The create game scene getter.
     * @return the create game scene.
     */
    public CreateGameScene getCreateGameScene() {
        return createGameScene;
    }

    /**
     * The lobby scene getter.
     * @return the lobby scene.
     */
    public LobbyScene getLobbyScene() {
        return lobbyScene;
    }
}
