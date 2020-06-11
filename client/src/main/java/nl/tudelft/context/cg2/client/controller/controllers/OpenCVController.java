package nl.tudelft.context.cg2.client.controller.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;
import nl.tudelft.context.cg2.client.Settings;
import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.io.posedetection.PoseDetector;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;
import com.github.sarxos.webcam.Webcam;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The OpenCV scene controller class.
 * Controls the OpenCV scene.
 */
public class OpenCVController {

    private Webcam webcam;
    private PoseDetector poseDetector;
    private Timeline captureTimer;

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
    public OpenCVController(Controller controller, Model model, View view) {
        this.controller = controller;
        this.model = model;
        this.view = view;
        this.webcam = null;
        this.poseDetector = null;
        this.captureTimer = null;
    }

    /**
     * Starts video capture and pose detection.
     */
    public void startCapture() {
        nu.pattern.OpenCV.loadLocally();

//        webcam = Webcam.getDefault();
        webcam = Webcam.getWebcamByName(Webcam.getWebcams().get(Webcam.getWebcams().size() - 1).getName());
//        webcam = Webcam.getWebcamByName(Webcam.getWebcams().get(0).getName());
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open(true);

        double fps = 15.0;
        poseDetector = new PoseDetector();

        captureTimer = new Timeline(new KeyFrame(Duration.seconds(1.0 / fps), event -> {
            long start = System.currentTimeMillis();
            captureAndProcessSnapshot(poseDetector);
            long end = System.currentTimeMillis();
            System.out.println("TOTAL MS: " + (end - start));
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
        webcam.close();
        webcam = null;
        poseDetector = null;
    }

    /**
     * Captures and processes a snapshot of webcam feed.
     * @param poseDetector - PoseDetector object
     */
    public void captureAndProcessSnapshot(PoseDetector poseDetector) {
        WritableImage writableImage = null;

        if (webcam.isOpen()) {
            long start = System.currentTimeMillis();
            BufferedImage webcamImage = webcam.getImage();
            long end = System.currentTimeMillis();
            System.out.println("Capture image: " + (end - start));

            BufferedImage image = poseDetector.generatePoseRegions(webcamImage);
            writableImage = SwingFXUtils.toFXImage(image, null);

            if (model.getCurrentPlayer() != null) {
                model.getCurrentPlayer().updatePose(poseDetector.getPose().copy());
            }
        }

        view.getGameScene().getCameraView().setImage(writableImage);
    }
}
