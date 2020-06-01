package nl.tudelft.context.cg2.client.controller.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;
import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.logic.posedetection.PoseDetector;
import nl.tudelft.context.cg2.client.controller.view.scenes.SettingsSceneController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.awt.image.BufferedImage;

import static org.opencv.imgproc.Imgproc.resize;

/**
 * The OpenCV scene controller class.
 * Controls the OpenCV scene.
 */
public class OpenCVController {

    private VideoCapture videoCapture;
    private PoseDetector poseDetector;
    private Timeline captureTimer;

    private final SettingsSceneController settingsSceneController;
    private final Controller controller;
    private final Model model;
    private View view;

    /**
     * The OpenCV scene controller.
     * Controls the input on the main scene.
     * @param controller the controller class.
     * @param model the model class.
     * @param view the view class.
     */
    public OpenCVController(Controller controller, Model model, View view,
                            SettingsSceneController settingsSceneController) {
        this.controller = controller;
        this.model = model;
        this.view = view;
        this.settingsSceneController = settingsSceneController;
        this.videoCapture = null;
        this.poseDetector = null;
        this.captureTimer = null;
    }

    /**
     * Starts video capture and pose detection.
     */
    public void startCapture() {
        nu.pattern.OpenCV.loadLocally();
        videoCapture = new VideoCapture();
        videoCapture.open(Math.max(settingsSceneController.getScene().getSelectedOption(), 0));

        double fps = 5.0;
        videoCapture.set(Videoio.CAP_PROP_FPS, fps);
        poseDetector = new PoseDetector();

        captureTimer = new Timeline(new KeyFrame(Duration.seconds(1.0 / fps), event -> {
            captureAndProcessSnapshot(poseDetector);
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
     */
    public void captureAndProcessSnapshot(PoseDetector poseDetector) {
        WritableImage writableImage = null;

        Mat matrix = new Mat();
        videoCapture.read(matrix);

        // Scale the image to 480p resolution
        resize(matrix, matrix, new Size(640, 480));

        if (videoCapture.isOpened()) {
            BufferedImage image = poseDetector.generatePoseRegions(matrix);
            writableImage = SwingFXUtils.toFXImage(image, null);

            if (model.getCurrentPlayer() != null) {
                model.getCurrentPlayer().updatePose(poseDetector.getPose().copy());
            }
        }

        view.getGameScene().getCameraView().setImage(writableImage);
    }
}
