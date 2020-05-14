package nl.tudelft.context.cg2.client.model.datastructures;

/**
 * A immutable implementation of a two dimensional vector.
 */
public class Vector2D {
    public final double x;
    public final double y;

    /**
     * Creates a Vector2D (0, 0).
     */
    public Vector2D() {
        this(0, 0);
    }

    /**
     * Creates a Vector2D (x, y).
     * @param x x
     * @param y y
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds two vectors.
     * @param other the other vector
     * @return the added vector
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    /**
     * Adds two vectors.
     * @param x the other vector's x component
     * @param y the other vector's y component
     * @return the added vector
     */
    public Vector2D add(double x, double y) {
        return new Vector2D(this.x + x, this.y + y);
    }

    /**
     * Subtracts two vectors.
     * @param other the other vector.
     * @return the subtracted vector.
     */
    public Vector2D sub(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    /**
     * Subtracts two vectors.
     * @param x the other vector's x component
     * @param y the other vector's y component
     * @return the subtracted vector
     */
    public Vector2D sub(double x, double y) {
        return new Vector2D(this.x - x, this.y - y);
    }

    /**
     * Multiplies two vectors.
     * @param other the other vector.
     * @return the multiplier vector.
     */
    public Vector2D mult(Vector2D other) {
        return new Vector2D(this.x * other.x, this.y * other.y);
    }

    /**
     * Multiplies two vectors.
     * @param x the other vector's x component
     * @param y the other vector's y component
     * @return the multiplied vector
     */
    public Vector2D mult(double x, double y) {
        return new Vector2D(this.x * x, this.y * y);
    }

    /**
     * Multiplies the 2D vector with a scalar.
     * @param scalar the scalar to multiply by.
     * @return the scaled vector.
     */
    public Vector2D mult(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    /**
     * Calculates the dot product of two vectors.
     * @param other the other vector.
     * @return the dot product.
     */
    public double dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    /**
     * Calculates the vector length.
     * @return the length of the vector.
     */
    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Calculates the squared length of the vector.
     * @return the length squared.
     */
    public double lengthSquared() {
        return x * x + y * y;
    }

    /**
     * Normalizes a vector to unit length.
     * @return the unit vector.
     */
    public Vector2D unit() {
        double multiplier = 1f / length();
        return new Vector2D(x * multiplier, y * multiplier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)  {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Vector2D vector2D = (Vector2D) o;

        if (Double.compare(vector2D.x, x) != 0) {
            return false;
        }
        return Double.compare(vector2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Vector2D{" + "x=" + x + ", y=" + y + '}';
    }
}

