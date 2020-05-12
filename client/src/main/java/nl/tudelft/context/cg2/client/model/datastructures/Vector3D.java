package nl.tudelft.context.cg2.client.model.datastructures;

/**
 * A immutable implementation of a three dimensional vector.
 */
public class Vector3D {
    public final double x;
    public final double y;
    public final double z;

    /**
     * Creates a Vector3D (0, 0).
     */
    public Vector3D() {
        this(0, 0, 0);
    }

    /**
     * Creates a Vector2D (x, y).
     * @param x x
     * @param y y
     * @param z z
     */
    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Adds two vectors.
     * @param other the other vector
     * @return the added vector
     */
    public Vector3D add(Vector3D other) {
        return new Vector3D(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    /**
     * Adds two vectors.
     * @param x the other vector's x component
     * @param y the other vector's y component
     * @param z the other vector's z component
     * @return the added vector
     */
    public Vector3D add(double x, double y, double z) {
        return new Vector3D(this.x + x, this.y + y, this.z + z);
    }

    /**
     * Subtracts two vectors.
     * @param other the other vector.
     * @return the subtracted vector.
     */
    public Vector3D sub(Vector3D other) {
        return new Vector3D(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    /**
     * Subtracts two vectors.
     * @param x the other vector's x component
     * @param y the other vector's y component
     * @param z the other vector's z component
     * @return the subtracted vector
     */
    public Vector3D sub(double x, double y, double z) {
        return new Vector3D(this.x - x, this.y - y, this.z - z);
    }

    /**
     * Multiplies two vectors.
     * @param other the other vector.
     * @return the multiplier vector.
     */
    public Vector3D mult(Vector3D other) {
        return new Vector3D(this.x * other.x, this.y * other.y, this.z * other.z);
    }

    /**
     * Multiplies two vectors.
     * @param x the other vector's x component
     * @param y the other vector's y component
     * @param z the other vector's z component
     * @return the multiplied vector
     */
    public Vector3D mult(double x, double y, double z) {
        return new Vector3D(this.x * x, this.y * y, this.z * z);
    }

    /**
     * Multiplies the 3D vector with a scalar.
     * @param scalar the scalar to multiply by.
     * @return the scaled vector.
     */
    public Vector3D mult(double scalar) {
        return new Vector3D(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    /**
     * Calculates the dot product of two vectors.
     * @param other the other vector.
     * @return the dot product.
     */
    public double dot(Vector3D other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    /**
     * Calculates the vector length.
     * @return the length of the vector.
     */
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Calculates the squared length of the vector.
     * @return the length squared.
     */
    public double lengthSquared() {
        return x * x + y * y + z * z;
    }

    /**
     * Normalizes a vector to unit length.
     * @return the unit vector.
     */
    public Vector3D unit() {
        double multiplier = 1f / length();
        return new Vector3D(x * multiplier, y * multiplier, z * multiplier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)  {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Vector3D vector3D = (Vector3D) o;

        if (Double.compare(vector3D.x, x) != 0) {
            return false;
        }
        if (Double.compare(vector3D.y, y) != 0) {
            return false;
        }
        return Double.compare(vector3D.z, z) == 0;
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
        return "Vector3D{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
}

