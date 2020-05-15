package nl.tudelft.context.cg2.client.view;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.scenes.CreateGameScene;
import nl.tudelft.context.cg2.client.view.scenes.GameScene;
import nl.tudelft.context.cg2.client.view.scenes.JoinScene;
import nl.tudelft.context.cg2.client.view.scenes.LobbyScene;
import nl.tudelft.context.cg2.client.view.scenes.MenuScene;
import nl.tudelft.context.cg2.client.view.scenes.OpenCVScene;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The view class.
 */
public class View {

    private final Model model;
    private final Window window;

    private final ArrayList<BaseScene> scenes;
    private final MenuScene menuScene;
    private final GameScene gameScene;
    private final JoinScene joinScene;
    private final CreateGameScene createGameScene;
    private final LobbyScene lobbyScene;
    private final OpenCVScene openCVScene;

    /**
     * The view constructor.
     * @param stage the javafx window being displayed to the user.
     * @param model the model that contains elements to be drawn in the window.
     */
    public View(final Stage stage, Model model) {
        this.model = model;
        this.window = new Window(stage);
        this.menuScene = new MenuScene(window, new StackPane());
        this.gameScene = new GameScene(window, new Pane(), model.getWorld());
        this.joinScene = new JoinScene(window, new StackPane());
        this.createGameScene = new CreateGameScene(window, new StackPane());
        this.lobbyScene = new LobbyScene(window, new StackPane());
        this.openCVScene = new OpenCVScene(window, new StackPane());

        this.scenes = new ArrayList<>(Arrays.asList(
                menuScene,
                gameScene,
                joinScene,
                createGameScene,
                lobbyScene,
                openCVScene
        ));

        scenes.forEach(BaseScene::draw);
    }

    /**
     * Updates everything that runs on the graphics timer.
     */
    public void update() {
        Platform.runLater(() -> window.getShownScene().animate());
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
     * The game scene getter.
     * @return the game scene.
     */
    public GameScene getGameScene() {
        return gameScene;
    }

    /**
     * The join scene getter.
     * @return the join scene.
     */
    public JoinScene getJoinScene() {
        return joinScene;
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

    /**
     * The openCV scene getter.
     * @return the openCV scene.
     */
    public OpenCVScene getOpenCVScene() {
        return openCVScene;
    }
}
