package nl.tudelft.context.cg2.client.view.scenes;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import nl.tudelft.context.cg2.client.view.BaseScene;
import nl.tudelft.context.cg2.client.view.Window;

/**
 * The OpenCV scene.
 * Shows the camera output in a imageview.
 */
public class OpenCVScene extends BaseScene {

    private ImageView video;

    /**
     * The lobby scene constructor.
     * @param window the window currently showing.
     * @param root   the root UI element.
     */
    public OpenCVScene(Window window, Pane root) {
        super(window, root);
    }

    /**
     * Draws the scene.
     */
    @Override
    public void draw() {
        video = new ImageView();
        video.setPreserveRatio(true);
        video.fitHeightProperty().bind(window.sceneHeightProperty());
        root.getChildren().add(video);
    }

    /**
     * Animates the scene.
     */
    @Override
    public void animate() {

    }

    /**
     * Event thrown when the scene is shown in the window.
     */
    @Override
    public void onShown() {

    }

    /**
     * Gets the redrawable image -> video.
     * @return the video view.
     */
    public ImageView getVideo() {
        return video;
    }
}
