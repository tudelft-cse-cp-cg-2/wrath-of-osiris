package nl.tudelft.context.cg2.client.controller.io.posedetection;

import nl.tudelft.context.cg2.client.Settings;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
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
    private List<PoseRegion> poseRegions;

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
     * @param image - the input image, a webcam frame
     * @return a list with six regions, in the order top -> bottom,
     *     right -> left. if no faces are found, this list will be empty.
     */
    public BufferedImage generatePoseRegions(BufferedImage image) {
        if (counter == 6 || faceDetections == null) {
            counter = 0;
            faceDetections = new MatOfRect();
            poseRegions = null;
            classifier.detectMultiScale(bufferedImageToMat(image), faceDetections);
        }
        counter++;

        // Detect
        PoseRegion head;
        if (faceDetections.toArray().length > 0 && poseRegions == null) {
            Rect rect = faceDetections.toArray()[0];
            head = new PoseRegion(rect.x, rect.y, rect.width, rect.height, null, null);
            poseRegions = generatePoseRegionsFromHead(head); // we always take the last found match
        } else {
            Settings.debugMessage("No face is detected");
            return image;
        }

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
//        BufferedImage tmp = deepCopy(img);
        // Loop over regions
        for (PoseRegion poseRegion : poseRegions) {
            // Loop over pixels; continue if poseRegion not fully in screen
            for (int x = poseRegion.getLeftX(); x < poseRegion.getRightX(); x++) {
                if (x < 0 || x > 639) {
                    continue;
                }
                for (int y = poseRegion.getTopY(); y < poseRegion.getBottomY(); y++) {
                    if (y < 0 || y > 479) {
                        continue;
                    }
                    int rgbValue = baseImage.getRGB(x, y);
                    int rgbValueImg = img.getRGB(x, y);
                    if (Math.abs(rgbValue - rgbValueImg) > epsilon) {
//                        img.setRGB(x, y, this.green);
                        this.pose.incrementCounter(poseRegion.getLimb(), poseRegion.getPosition());
                    }
                }
            }
        }
//        this.baseImage = tmp;
        this.baseImage = img;
        return img;
    }

//    static BufferedImage deepCopy(BufferedImage bi) {
//        ColorModel cm = bi.getColorModel();
//        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
//        WritableRaster raster = bi.copyData(null);
//        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
//    }

    /**
     * A getter for the pose.
     * @return The pose.
     */
    public Pose getPose() {
        return pose;
    }

    /**
     * Get the matrix from a BufferedImage.
     * @param bi the bufferedImage object.
     * @return The matrix.
     */
    public static Mat bufferedImageToMat(BufferedImage bi) {
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }
}
