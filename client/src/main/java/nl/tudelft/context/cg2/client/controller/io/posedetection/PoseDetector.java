package nl.tudelft.context.cg2.client.controller.io.posedetection;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for detecting a player's body posture.
 */
public class PoseDetector {
    private static final String POSE_DETECTION_DEFAULT = "haarcascade_frontalface_default.xml";

    private final CascadeClassifier classifier = new CascadeClassifier(POSE_DETECTION_DEFAULT);
    private final int green = new Color(0, 255, 0).getRGB();

    private final Pose pose = new Pose(ScreenPos.middle, Position.bottom, Position.bottom,
            Position.neutral, Position.neutral);
    private BufferedImage baseImage;

    private int counter = 0;
    private MatOfRect faceDetections;

    /**
     * Given the coordinates of a head, calculate the bounding boxes
     * corresponding to the arm positions.
     * @param head - coordinates of the detected head
     * @return a list with six regions, in the order top -> bottom,
     *     right -> left. if no faces are found, this list will be empty.
     */
    private List<PoseRegion> generatePoseRegionsFromHead(PoseRegion head) {
        List<PoseRegion> out = new ArrayList<>();

        // arm top left
        out.add(new PoseRegion(head.getLeftX() + (int) (1.2 * head.getRightX()),
                head.getTopY() - 3 * head.getBottomY(),
                head.getLeftX() + 3 * head.getRightX(),
                head.getTopY() + head.getBottomY(),
                Limb.left_arm, Position.top));
        // arm middle left
        out.add(new PoseRegion(head.getLeftX() + (int) (2.5 * head.getRightX()),
                head.getTopY() + head.getBottomY() + 10,
                head.getLeftX() + 6 * head.getRightX(),
                head.getTopY() + 3 * head.getBottomY(),
                Limb.left_arm, Position.middle));
        // arm bottom left
        out.add(new PoseRegion(head.getLeftX() + (int) (1.5 * head.getRightX()),
                head.getTopY() + 3 * head.getBottomY(),
                head.getLeftX() + 3 * head.getRightX(),
                head.getTopY() + 5 * head.getBottomY(),
                Limb.left_arm, Position.bottom));

        // arm top right
        out.add(new PoseRegion(head.getLeftX() - 2 * head.getRightX(),
                head.getTopY() - 3 * head.getBottomY(),
                head.getLeftX() - (int) (0.2 * head.getRightX()),
                head.getTopY() + head.getBottomY(),
                Limb.right_arm, Position.top));
        // arm middle right
        out.add(new PoseRegion(head.getLeftX() - 5 * head.getRightX(),
                head.getTopY() + head.getBottomY() + 10,
                head.getLeftX() - (int) (1.5 * head.getRightX()),
                head.getTopY() + 3 * head.getBottomY(),
                Limb.right_arm, Position.middle));
        // arm bottom right
        out.add(new PoseRegion(head.getLeftX() - 2 * head.getRightX(),
                head.getTopY() + 3 * head.getBottomY(),
                head.getLeftX() - (int) (0.5 * head.getRightX()),
                head.getTopY() + 5 * head.getBottomY(),
                Limb.right_arm, Position.bottom));


        // leg neutral left
        out.add(new PoseRegion(head.getLeftX() + (int) (0.5 * head.getRightX()),
                head.getTopY() + 5 * head.getBottomY(),
                head.getLeftX() + 2 * head.getRightX(),
                head.getTopY() + 10 * head.getBottomY(),
                Limb.left_leg, Position.neutral));
        // leg raised left
        out.add(new PoseRegion(head.getLeftX() + 2 * head.getRightX(),
                head.getTopY() + 5 * head.getBottomY(),
                head.getLeftX() + (int) (3.5 * head.getRightX()),
                head.getTopY() + 10 * head.getBottomY(),
                Limb.left_leg, Position.raised));

        // leg neutral right
        out.add(new PoseRegion(head.getLeftX() - head.getRightX(),
                head.getTopY() + 5 * head.getBottomY(),
                head.getLeftX() + (int) (0.5 * head.getRightX()),
                head.getTopY() + 10 * head.getBottomY(),
                Limb.right_leg, Position.neutral));
        // leg raised right
        out.add(new PoseRegion(head.getLeftX() - (int) (2.5 * head.getRightX()),
                head.getTopY() + 5 * head.getBottomY(),
                head.getLeftX() - head.getRightX(),
                head.getTopY() + 10 * head.getBottomY(),
                Limb.right_leg, Position.raised));
        return out;
    }

    /**
     * Detect faces in an image, and return the six bounding boxes in which
     * the arms might be found. In case of multiple faces, take the first
     * detected match.
     * @param matrix - the input image, a webcam frame
     * @return a list with six regions, in the order top -> bottom,
     *     right -> left. if no faces are found, this list will be empty.
     */
    public BufferedImage generatePoseRegions(Mat matrix) {
        if (counter == 5 || faceDetections == null) {
            counter = 0;
            faceDetections = new MatOfRect();
            classifier.detectMultiScale(matrix, faceDetections);
        }
        counter++;

        BufferedImage image =
                new BufferedImage(matrix.width(), matrix.height(), BufferedImage.TYPE_3BYTE_BGR);
        List<PoseRegion> poseRegions;

        // Detect
        PoseRegion head;
        if (faceDetections.toArray().length > 0) {
            Rect rect = faceDetections.toArray()[0];
            head = new PoseRegion(rect.x, rect.y, rect.width, rect.height, null, null);
            poseRegions = generatePoseRegionsFromHead(head); // we always take the last found match
        } else {
            System.out.println("No face is detected");
            Imgproc.putText(
                    matrix, "No face detected", new Point(10, 50), 1, 3, new Scalar(0, 0, 255), 4
            );
            WritableRaster raster = image.getRaster();
            DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
            byte[] data = dataBuffer.getData();
            matrix.get(0, 0, data);
            return image;
        }

//        for (PoseRegion poseRegion : poseRegions) {
//            Imgproc.rectangle(matrix, poseRegion.getTopLeft(), poseRegion.getBottomRight(),
//                    new Scalar(255, 0, 0), 3, 0, 0);
//        }

        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        matrix.get(0, 0, data);

        if (baseImage == null) {
            baseImage = image;
        } else {
            image = findLimbLocations(poseRegions, image, head);
        }

        return image;
    }

    /**
     * Finds a player's limbs.
     * @param poseRegions - regions in which limbs could be
     * @param image - image to find the limbs in
     * @param head - coordinates of the detected head
     * @return an image with the pose marked
     */
    public BufferedImage findLimbLocations(List<PoseRegion> poseRegions, BufferedImage image,
                                           PoseRegion head) {
        this.pose.resetCounters();
        BufferedImage bufferedImage = blendAndCompareImages(poseRegions, image);
        this.pose.updateScreenPosition(head);
        this.pose.updatePose();
        return bufferedImage;
    }

    /**
     * Show temporal differences between the current frame and previous ones.
     * @param poseRegions - regions in which limbs could be
     * @param img - image to find limbs in
     * @return an image with the pose marked
     */
    BufferedImage blendAndCompareImages(List<PoseRegion> poseRegions, BufferedImage img) {
        int epsilon = 750000;
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int rgbValue = (baseImage.getRGB(x, y) + img.getRGB(x, y)) / 2;
                baseImage.setRGB(x, y, rgbValue);
                int rgbValueImg = img.getRGB(x, y);
                if (Math.abs(rgbValue - rgbValueImg) > epsilon) {
                    img.setRGB(x, y, this.green);
                    for (PoseRegion poseRegion : poseRegions) {
                        if (poseRegion.inRange(x, y)) {
                            this.pose.incrementCounter(poseRegion.getLimb(),
                                    poseRegion.getPosition());
                        }
                    }
                }
            }
        }
        return img;
    }

    /**
     * A getter for the pose.
     * @return The pose.
     */
    public Pose getPose() {
        return pose;
    }
}