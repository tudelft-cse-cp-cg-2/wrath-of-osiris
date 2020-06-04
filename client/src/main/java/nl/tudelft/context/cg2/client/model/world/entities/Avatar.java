package nl.tudelft.context.cg2.client.model.world.entities;

import javafx.scene.paint.Color;
import nl.tudelft.context.cg2.client.controller.io.posedetection.Pose;
import nl.tudelft.context.cg2.client.controller.io.textures.TextureFactory;
import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;
import nl.tudelft.context.cg2.client.model.world.Entity;
import nl.tudelft.context.cg2.client.model.world.World;
import nl.tudelft.context.cg2.client.model.world.factories.EntityFactory;
import nl.tudelft.context.cg2.client.model.world.superscripts.PlayerName;

/**
 * The avatar class.
 * Features a player avatar.
 */
public class Avatar extends Entity {
    private final Color color;

    /**
     * The avatar constructor.
     * @param playerName the name of the player this avatar is attached to.
     * @param color the color of the avatar.
     */
    public Avatar(Color color, String playerName) {
        super(TextureFactory.defaultAvatarTexture(color), new PlayerName(playerName, color),
                new Vector3D(), new Vector3D(), EntityFactory.HOLE_SIZE);
        this.color = color;
    }

    @Override
    public void step(double t, double dt) {
        throw new RuntimeException("Wrong step function called.");
    }

    /**
     * Step function.
     * @param pose the new pose.
     */
    public void step(Pose pose) {
        updatePose(pose);
        updatePosition(pose);
    }

    /**
     * Updates the avatar texture based on a pose.
     * @param pose the pose of the avatar's player.
     */
    private void updatePose(Pose pose) {
        this.setTexture(TextureFactory.avatarTexture(pose, color));
    }

    /**
     * Updates the avatars position in the world based on the pose.
     * @param pose the pose of the avatar's player.
     */
    private void updatePosition(Pose pose) {
        switch (pose.getScreenPosition()) {
            case left:
                setPosition(new Vector3D(
                        World.WIDTH / 4 - EntityFactory.HOLE_SIZE.x / 2, 0, 0));
                break;
            case middle:
                setPosition(new Vector3D((
                        World.WIDTH / 2 - EntityFactory.HOLE_SIZE.x / 2), 0, 0));
                break;
            case right:
                setPosition(new Vector3D((
                        World.WIDTH / 4 * 3 - EntityFactory.HOLE_SIZE.x / 2), 0, 0));
                break;
            default: throw new IllegalStateException("Incorrect screenpos: "
                    + pose.getScreenPosition());
        }
    }
}
