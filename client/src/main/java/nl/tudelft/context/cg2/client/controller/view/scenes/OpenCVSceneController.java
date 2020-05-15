package nl.tudelft.context.cg2.client.controller.view.scenes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;
import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.view.SceneController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.posedetection.PoseDetector;
import nl.tudelft.context.cg2.client.view.View;
import nl.tudelft.context.cg2.client.view.scenes.OpenCVScene;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;

import static org.opencv.core.Core.flip;

/**
 * The OpenCV scene controller class.
 * Controls the OpenCV scene.
 */
public class OpenCVSceneController extends SceneController {

    private final OpenCVScene scene;

    private VideoCapture videoCapture;
    private WritableImage writableImage;
    private PoseDetector poseDetector;
    private Timeline captureTimer;
    private boolean skip;


    /**
     * The OpenCV scene controller.
     * Controls the input on the main scene.
     * @param controller the controller class.
     * @param model the model class.
     * @param view the view class.
     */
    public OpenCVSceneController(Controller controller, Model model, View view) {
        super(controller, model, view);
        scene = view.getOpenCVScene();
        this.videoCapture = null;
        this.poseDetector = null;
        this.writableImage = null;
        this.captureTimer = null;
        this.skip = false;
    }

    /**
     * Sets up the mouse listeners attached to the various GUI elements.
     */
    @Override
    protected void setupMouseListeners() {

    }

    /**
     * Sets up the keyboard listeners attached to the various GUI elements.
     */
    @Override
    protected void setupKeyboardListeners() {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case BACK_SPACE:
                    stopCapture();
                    view.getMenuScene().show();
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * Sets up the event listeners attach to various UI properties.
     */
    @Override
    protected void setupEventListeners() {

    }

    /**
     * Starts video capture and pose detection.
     */
    public void startCapture() {
        nu.pattern.OpenCV.loadLocally();
        videoCapture = new VideoCapture();
        videoCapture.open(0);
        poseDetector = new PoseDetector();

        captureTimer = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
            scene.getVideo().setImage(captureAndProcessSnapshot(poseDetector));
        }));
        captureTimer.setCycleCount(Timeline.INDEFINITE);
        captureTimer.play();
    }

    /**
     * Stops video capture and pose detection.
     */
    public void stopCapture() {
        captureTimer.stop();
        captureTimer = null;
        videoCapture.release();
        videoCapture = null;
        poseDetector = null;
    }

    /**
     * Captures and processes a snapshot of webcam feed.
     * @param poseDetector - PoseDetector object
     * @return an image with the player pose marked
     */
    public WritableImage captureAndProcessSnapshot(PoseDetector poseDetector) {
        WritableImage writableImage = null;

        Mat matrix = new Mat();
        videoCapture.read(matrix);

        if (skip) {
            skip = false;
            return this.writableImage;
        }

        flip(matrix, matrix, +1);
        if (videoCapture.isOpened()) {
            BufferedImage image = poseDetector.generatePoseRegions(matrix);
            writableImage = SwingFXUtils.toFXImage(image, null);
        }

        skip = true;
        this.writableImage = writableImage;

        return writableImage;
    }
}
