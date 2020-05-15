package nl.tudelft.context.cg2.client;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
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
    private final CascadeClassifier classifier = new CascadeClassifier(
            "src/main/java/nl/tudelft/context/cg2/client/haarcascade_frontalface_default.xml");
    private final int green = new Color(0, 255, 0).getRGB();

    private final Pose pose = new Pose(0, 0, 0, 0);
    private BufferedImage baseImage;

    /**
     * Given the coordinates of a head, calculate the bounding boxes
     * corresponding to the arm positions.
     * @param head - coordinates of the detected head
     * @return a list with six regions, in the order top -> bottom,
     *     right -> left. if no faces are found, this list will be empty.
     */
    private List<PoseRegion> generatePoseRegionsFromHead(PoseRegion head) {
        List<PoseRegion> out = new ArrayList<>();

        // arm top right
        out.add(new PoseRegion(head.getTopLeftX() + head.getBottomRightX(),
                head.getTopLeftY() - 3 * head.getBottomRightY(),
                head.getTopLeftX() + 3 * head.getBottomRightX(),
                head.getTopLeftY() + head.getBottomRightY(),
                1, 0));
        // arm middle right
        out.add(new PoseRegion(head.getTopLeftX() + (int) (1.5 * (double) head.getBottomRightX()),
                head.getTopLeftY() + head.getBottomRightY() + 10,
                head.getTopLeftX() + 5 * head.getBottomRightX(),
                head.getTopLeftY() + 3 * head.getBottomRightY(),
                1, 1));
        // arm bottom right
        out.add(new PoseRegion(head.getTopLeftX() + head.getBottomRightX(),
                head.getTopLeftY() + 2 * head.getBottomRightY(),
                head.getTopLeftX() + 3 * head.getBottomRightX(),
                head.getTopLeftY() + 5 * head.getBottomRightY(),
                1, 2));
        // arm top left
        out.add(new PoseRegion(head.getTopLeftX(),
                head.getTopLeftY() - 3 * head.getBottomRightY(),
                head.getTopLeftX() - 2 * head.getBottomRightX(),
                head.getTopLeftY() + head.getBottomRightY(),
                0, 0));
        // arm middle left
        out.add(new PoseRegion(head.getTopLeftX() - (int) (0.5 * (double) head.getBottomRightX()),
                head.getTopLeftY() + head.getBottomRightY() + 10,
                head.getTopLeftX() - 4 * head.getBottomRightX(),
                head.getTopLeftY() + 3 * head.getBottomRightY(),
                0, 1));
        // arm bottom left
        out.add(new PoseRegion(head.getTopLeftX(),
                head.getTopLeftY() + 2 * head.getBottomRightY(),
                head.getTopLeftX() - 2 * head.getBottomRightX(),
                head.getTopLeftY() + 5 * head.getBottomRightY(),
                0, 2));

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
        MatOfRect faceDetections = new MatOfRect();
        classifier.detectMultiScale(matrix, faceDetections);
        BufferedImage image =
                new BufferedImage(matrix.width(), matrix.height(), BufferedImage.TYPE_3BYTE_BGR);
        List<PoseRegion> poseRegions;

        // Detect
        if (faceDetections.toArray().length > 0) {
            Rect rect = faceDetections.toArray()[0];
            PoseRegion head = new PoseRegion(rect.x, rect.y, rect.width, rect.height, -1, -1);
            poseRegions = generatePoseRegionsFromHead(head); // we always take the last found match
        } else {
            return image;
        }

        for (PoseRegion poseRegion : poseRegions) {
            Imgproc.rectangle(matrix, poseRegion.getTopLeft(), poseRegion.getBottomRight(),
                    new Scalar(255, 0, 0), 3, 0, 0);
        }

        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        matrix.get(0, 0, data);

        if (baseImage == null) {
            baseImage = image;
        } else {
            image = findLimbLocations(poseRegions, image);
        }

        return image;
    }

    /**
     * Finds a player's limbs.
     * @param poseRegions - regions in which limbs could be
     * @param image - image to find the limbs in
     * @return an image with the pose marked
     */
    public BufferedImage findLimbLocations(List<PoseRegion> poseRegions, BufferedImage image) {
        this.pose.resetCounters();
        BufferedImage bufferedImage = blendAndCompareImages(poseRegions, image);
        this.pose.updatePose();
        System.out.println(this.pose.toString());
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
                                    poseRegion.getOption());
                        }
                    }
                }
            }
        }
        return img;
    }


}
