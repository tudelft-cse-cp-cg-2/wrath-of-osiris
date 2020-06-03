package nl.tudelft.context.cg2.client.model.world.entities;

import nl.tudelft.context.cg2.client.controller.io.posedetection.Pose;
import nl.tudelft.context.cg2.client.controller.io.textures.TextureFactory;
import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;
import nl.tudelft.context.cg2.client.model.world.Entity;
import nl.tudelft.context.cg2.client.model.world.World;
import nl.tudelft.context.cg2.client.model.world.factories.EntityFactory;
import nl.tudelft.context.cg2.client.model.world.superscripts.HoleNumber;

/**
 * The hole class.
 * Features a hole in a wall.
 */
public class Hole extends Entity {

    private final Pose pose;
    private final int number;

    /**
     * The hole constructor.
     * @param position the position of the hole in the world.
     * @param velocity the velocity the hole is moving at.
     */
    public Hole(Vector3D position, Vector3D velocity, Pose pose, int number) {
        super(TextureFactory.holeTexture(pose), new HoleNumber(number), position, velocity, EntityFactory.HOLE_SIZE);
        this.pose = pose;
        this.number = number;
    }

    @Override
    public void step(double t, double dt) {
        if (getPosition().z > 0) {
            setPosition(getPosition().add(getVelocity().mult(dt)));
        }
    }

    /**
     * Gets the pose that matched the hole in the wall.
     * @return the pose field.
     */
    public Pose getPose() {
        return pose;
    }

    /**
     * Gets the number of people that need to pass through the hole.
     * @return the number field.
     */
    public int getNumber() {
        return number;
    }
}
