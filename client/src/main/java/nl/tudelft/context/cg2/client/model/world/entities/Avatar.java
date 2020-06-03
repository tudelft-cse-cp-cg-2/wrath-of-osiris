package nl.tudelft.context.cg2.client.model.world.entities;

import javafx.scene.paint.Color;
import nl.tudelft.context.cg2.client.controller.io.posedetection.Pose;
import nl.tudelft.context.cg2.client.controller.io.textures.TextureFactory;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;
import nl.tudelft.context.cg2.client.model.world.Entity;
import nl.tudelft.context.cg2.client.model.world.World;
import nl.tudelft.context.cg2.client.model.world.superscripts.PlayerName;

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
        super(TextureFactory.defaultAvatarTexture(color), new PlayerName(player.getName()),
                new Vector3D(), new Vector3D(), World.HOLE_SIZE);
        this.player = player;
        this.color = color;
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
}
