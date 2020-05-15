package nl.tudelft.context.cg2.client;

import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;

import java.util.ArrayList;
import java.util.List;

public class PoseDetector {
    private final CascadeClassifier classifier = new CascadeClassifier("/home/mpm/Git/main-repository/client/src/main/java/nl/tudelft/context/cg2/client/haarcascade_frontalface_default.xml");

    /**
     * Given the coordinates of a head, calculate the bounding boxes
     * corresponding to the arm positions.
     * @param head - coordinates of the detected head
     * @return a list with six regions, in the order top -> bottom,
     * right -> left. if no faces are found, this list will be empty.
     */
    private List<PoseRegion> generatePoseRegionsFromHead(PoseRegion head) {
        List<PoseRegion> out = new ArrayList<>();

        // arm top right
        out.add(new PoseRegion(head.getTopLeftX() + head.getBottomRightX(),
                head.getTopLeftY() - 3 * head.getBottomRightY(),
                head.getTopLeftX() + 3 * head.getBottomRightX(),
                head.getTopLeftY() + head.getBottomRightY()));
        // arm middle right
        out.add(new PoseRegion(head.getTopLeftX() + (int)(1.5 * (double)head.getBottomRightX()),
                head.getTopLeftY() + head.getBottomRightY() + 10,
                head.getTopLeftX() + 5 * head.getBottomRightX(),
                head.getTopLeftY() + 3 * head.getBottomRightY()));
        // arm bottom right
        out.add(new PoseRegion(head.getTopLeftX() + head.getBottomRightX(),
                head.getTopLeftY() + 2 * head.getBottomRightY(),
                head.getTopLeftX() + 3 * head.getBottomRightX(),
                head.getTopLeftY() + 5 * head.getBottomRightY()));
        // arm top left
        out.add(new PoseRegion(head.getTopLeftX(),
                head.getTopLeftY() - 3 * head.getBottomRightY(),
                head.getTopLeftX() - 2 * head.getBottomRightX(),
                head.getTopLeftY() + head.getBottomRightY()));
        // arm middle left
        out.add(new PoseRegion(head.getTopLeftX() - (int)(0.5 * (double)head.getBottomRightX()),
                head.getTopLeftY() + head.getBottomRightY() + 10,
                head.getTopLeftX() - 4 * head.getBottomRightX(),
                head.getTopLeftY() + 3 * head.getBottomRightY()));
        // arm bottom left
        out.add(new PoseRegion(head.getTopLeftX(),
                head.getTopLeftY() + 2 * head.getBottomRightY(),
                head.getTopLeftX() - 2 * head.getBottomRightX(),
                head.getTopLeftY() + 5 * head.getBottomRightY()));

        return out;
    }

    /**
     * Detect faces in an image, and return the six bounding boxes in which
     * the arms might be found. In case of multiple faces, take the first
     * detected match.
     * @param matrix - the input image, a webcam frame
     * @return a list with six regions, in the order top -> bottom,
     * right -> left. if no faces are found, this list will be empty.
     */
    public List<PoseRegion> generatePoseRegions(Mat matrix) {
        MatOfRect faceDetections = new MatOfRect();
        classifier.detectMultiScale(matrix, faceDetections);

        // Loop through detections
        if (faceDetections.toArray().length > 0) {
            Rect rect = faceDetections.toArray()[0];
            PoseRegion head = new PoseRegion(rect.x, rect.y, rect.width, rect.height);
            return generatePoseRegionsFromHead(head); // we always take the last found match
        } else {
            return new ArrayList<>();
        }
    }

    public boolean findLimbLocations() {
        return false;
    }
}
