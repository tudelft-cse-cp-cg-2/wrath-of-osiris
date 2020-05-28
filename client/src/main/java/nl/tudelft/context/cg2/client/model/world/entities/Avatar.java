package nl.tudelft.context.cg2.client.model.world.entities;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import nl.tudelft.context.cg2.client.controller.logic.posedetection.Pose;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
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
    private final Player player;

    /**
     * The avatar constructor.
     * @param player the player this avatar is attached to.
     * @param color the color of the avatar.
     */
    public Avatar(Player player, Color color) {
        super(null, new Vector3D(), new Vector3D(), World.HOLE_SIZE);
        this.player = player;
        this.color = color;
        this.setDefaultTexture();
    }

    @Override
    public void step(double t, double dt) {
        if (player.isPoseChanged()) {
            Pose pose = player.getPose();
            updatePose(pose);
            updatePosition(pose);
            player.setPoseChanged(false);
        }
    }

    /**
     * Updates the avatars position in the world based on the pose.
     * @param pose the pose of the avatar's player.
     */
    private void updatePosition(Pose pose) {
        switch (pose.getScreenPosition()) {
            case left:
                setPosition(new Vector3D(
                        World.WIDTH / 4 - World.HOLE_SIZE.x / 2, 0, 0));
                break;
            case middle:
                setPosition(new Vector3D((
                        World.WIDTH / 2 - World.HOLE_SIZE.x / 2), 0, 0));
                break;
            case right:
                setPosition(new Vector3D((
                        World.WIDTH / 4 * 3 - World.HOLE_SIZE.x / 2), 0, 0));
                break;
            default: throw new IllegalStateException("Incorrect screenpos: "
                    + pose.getScreenPosition());
        }
    }

    /**
     * Updates the avatar texture based on a pose.
     * @param pose the pose of the avatar's player.
     */
    private void updatePose(Pose pose) {
        int la = pose.getLeftArm().indexOf();
        int ra = pose.getRightArm().indexOf();
        int ll = pose.getLeftLeg().indexOf();
        int rl = pose.getRightLeg().indexOf();
        updateTexture(la, ra, ll, rl);
    }

    /**
     * Sets the avatars default texture.
     */
    public void setDefaultTexture() {
        updateTexture(0, 0, 0, 0);
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
