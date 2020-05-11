package nl.tudelft.context.cg2.client.model.world;

import javafx.scene.image.Image;
import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;

public abstract class Entity {

    private Image texture;
    private Vector3D position;
    private Vector3D velocity;
    private Vector3D size;

    public Entity(Image texture, Vector3D position, Vector3D velocity, Vector3D size) {
        this.texture = texture;
        this.position = position;
        this.velocity = velocity;
        this.size = size;
    }

    public abstract void step(double t, double dt);

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public Vector3D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3D velocity) {
        this.velocity = velocity;
    }

    public Vector3D getSize() {
        return size;
    }

    public void setSize(Vector3D size) {
        this.size = size;
    }
}
