package nl.tudelft.context.cg2.client.model.world.entities;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;
import nl.tudelft.context.cg2.client.model.files.ImageCache;
import nl.tudelft.context.cg2.client.model.world.Entity;
import nl.tudelft.context.cg2.client.model.world.World;

/**
 * The avatar class.
 * Features a player avatar.
 */
public class Avatar extends Entity {

    private final Color color;

    /**
     * The avatar constructor.
     * @param color the color of the avatar.
     */
    public Avatar(Color color) {
        super(null, new Vector3D(), new Vector3D(), World.HOLE_SIZE);
        this.color = color;
        this.updateTexture(1, 2, 0, 1);
    }

    @Override
    public void step(double t, double dt) {
        if (Math.random() > 0.95D) {
            randomTexture();
        }

    }

    /**
     * Creates a texture for the avatar with a random pose.
     */
    private void randomTexture() {
        this.updateTexture((int) Math.round(Math.random() * 2),
                (int) Math.round(Math.random() * 2),
                (int) Math.round(Math.random()),
                (int) Math.round(Math.random()));
    }

    /**
     * Updates the avatar's texture when a pose changes.
     * @param leftArmId the posture id of the left arm.
     * @param rightArmId the posture id of the right arm.
     * @param leftLegId the posture id of the left leg.
     * @param rightLegId the posture id of the right leg.
     */
    private void updateTexture(int leftArmId, int rightArmId, int leftLegId, int rightLegId) {
        int frameWidth = 134;
        int frameHeight = 178;
        int armHeight = 104;
        int legHeight = 74;

        WritableImage tex = new WritableImage(frameWidth, frameHeight);
        tex.getPixelWriter().setPixels(0, 0, frameWidth / 2, armHeight,
                ImageCache.IMAGES[9].getPixelReader(), 0, armHeight * leftArmId);
        tex.getPixelWriter().setPixels(frameWidth / 2, 0, frameWidth / 2, armHeight,
                ImageCache.IMAGES[10].getPixelReader(), 0, armHeight * rightArmId);
        tex.getPixelWriter().setPixels(0, armHeight, frameWidth / 2, legHeight,
                ImageCache.IMAGES[11].getPixelReader(), 0, legHeight * leftLegId);
        tex.getPixelWriter().setPixels(frameWidth / 2, armHeight, frameWidth / 2, legHeight,
                ImageCache.IMAGES[12].getPixelReader(), 0, legHeight * rightLegId);

        for (int x = 0; x < frameWidth; x++) {
            for (int y = 0; y < frameHeight; y++) {
                if (!tex.getPixelReader().getColor(x, y).equals(Color.TRANSPARENT)) {
                    tex.getPixelWriter().setColor(x, y, color);
                }
            }
        }

        setTexture(tex);
    }

}
