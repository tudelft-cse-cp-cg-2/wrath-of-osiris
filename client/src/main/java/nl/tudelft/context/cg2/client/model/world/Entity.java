package nl.tudelft.context.cg2.client.model.world;

import javafx.scene.image.Image;
import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;
import nl.tudelft.context.cg2.client.model.world.superscripts.Superscript;

/**
 * The entity abstract class.
 * Features a game entity that exists within the game world.
 */
public abstract class Entity {

    private Image texture;
    private Superscript superscript;

    private Vector3D position;
    private Vector3D velocity;
    private Vector3D size;

    /**
     * The entity constructor.
     * @param texture the texture of the entity.
     * @param superscript the script displayed above the entity.
     * @param position the position of the entity.
     * @param velocity the velocity of the entity.
     * @param size the size of the entity.
     */
    public Entity(Image texture, Superscript superscript, Vector3D position,
                  Vector3D velocity, Vector3D size) {
        this.texture = texture;
        this.superscript = superscript;
        this.position = position;
        this.velocity = velocity;
        this.size = size;
    }

    /**
     * Makes the entity progress one time step in the game world.
     * @param t the passed time in s since timer initialization.
     * @param dt the passed time in s since the last step.
     */
    public abstract void step(double t, double dt);

    /**
     * Gets the depth scalar that scales the entity's
     * size with depth perception.
     * @return the depth scalar.
     */
    public double getDepthScalar() {
        double slope = 60D;
        double scalar = slope / (position.z + slope);
        return scalar > 1D ? 1D : scalar;
    }

    /**
     * Gets the depth value of the entity in the world.
     * @return the depth value.
     */
    public Double getDepth() {
        return position == null ? 0D : position.z;
    }

    /**
     * The texture getter.
     * @return the texture of the entity.
     */
    public Image getTexture() {
        return texture;
    }

    /**
     * Sets the entity texture.
     * @param texture the texture to set.
     */
    public void setTexture(Image texture) {
        this.texture = texture;
    }

    /**
     * Gets the 3D position of the entity in the world.
     * @return the position vector.
     */
    public Vector3D getPosition() {
        return position;
    }

    /**
     * Sets the 3D position of the entity in the world.
     * @param position the entity position vector.
     */
    public void setPosition(Vector3D position) {
        this.position = position;
    }

    /**
     * Gets the 3D velocity of the entity in the world.
     * @return the velocity vector.
     */
    public Vector3D getVelocity() {
        return velocity;
    }

    /**
     * Sets the 3D velocity of the entity in the world.
     * @param velocity the entity velocity vector.
     */
    public void setVelocity(Vector3D velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets the size dimensions of the entity.
     * @return the size vector.
     */
    public Vector3D getSize() {
        return size;
    }

    /**
     * Sets the entity size.
     * @param size the size of the entity.
     */
    public void setSize(Vector3D size) {
        this.size = size;
    }

    /**
     * Gets the superscript attached to this entity.
     * @return the superscript attached to this entity.
     */
    public Superscript getSuperscript() {
        return superscript;
    }

    /**
     * Sets the superscript attached to this entity.
     * @param superscript the superscript to set.
     */
    public void setSuperscript(Superscript superscript) {
        this.superscript = superscript;
    }
}
