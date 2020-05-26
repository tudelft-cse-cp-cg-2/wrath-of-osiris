package nl.tudelft.context.cg2.client.model.world.entities;

import javafx.scene.image.WritableImage;
import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;
import nl.tudelft.context.cg2.client.model.files.ImageCache;
import nl.tudelft.context.cg2.client.model.world.Entity;
import nl.tudelft.context.cg2.client.model.world.World;

/**
 * The hole class.
 * Features a hole in a wall.
 */
public class Hole extends Entity {

    /**
     * The hole constructor.
     * @param position the position of the hole in the world.
     * @param velocity the velocity the hole is moving at.
     */
    public Hole(Vector3D position, Vector3D velocity) {
        super(null, position, velocity, World.HOLE_SIZE.mult(1.1D));
        this.randomTexture();
    }

    @Override
    public void step(double t, double dt) {
        if (getPosition().z > 0) {
            setPosition(getPosition().add(getVelocity().mult(dt)));
        }
    }

    /**
     * Creates a texture for the hole with a random pose.
     */
    private void randomTexture() {
        this.updateTexture((int) Math.round(Math.random() * 2),
                (int) Math.round(Math.random() * 2),
                (int) Math.round(Math.random()),
                (int) Math.round(Math.random()));
    }

    /**
     * Updates the hole's texture to match a given post.
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
                ImageCache.IMAGES[13].getPixelReader(), 0, armHeight * leftArmId);
        tex.getPixelWriter().setPixels(frameWidth / 2, 0, frameWidth / 2, armHeight,
                ImageCache.IMAGES[14].getPixelReader(), 0, armHeight * rightArmId);
        tex.getPixelWriter().setPixels(0, armHeight, frameWidth / 2, legHeight,
                ImageCache.IMAGES[15].getPixelReader(), 0, legHeight * leftLegId);
        tex.getPixelWriter().setPixels(frameWidth / 2, armHeight, frameWidth / 2, legHeight,
                ImageCache.IMAGES[16].getPixelReader(), 0, legHeight * rightLegId);
        setTexture(tex);
    }




}
