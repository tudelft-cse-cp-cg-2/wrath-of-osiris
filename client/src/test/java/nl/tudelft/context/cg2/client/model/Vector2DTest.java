package nl.tudelft.context.cg2.client.model;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.context.cg2.client.model.datastructures.Vector2D;
import org.junit.jupiter.api.Test;

public class Vector2DTest {
    @Test
    public void testEmptyConstructor() {
        Vector2D v = new Vector2D();

        assertEquals(0, v.x);
        assertEquals(0, v.y);
    }

    @Test
    public void testAdd1() {
        Vector2D v = new Vector2D(2,3).add(new Vector2D(4,5));

        assertEquals(6, v.x);
        assertEquals(8, v.y);
    }

    @Test
    public void testAdd2() {
        Vector2D v = new Vector2D(2,3).add(4,5);

        assertEquals(6, v.x);
        assertEquals(8, v.y);
    }

    @Test
    public void testSub1() {
        Vector2D v = new Vector2D(2,-4).sub(new Vector2D(4,-5));

        assertEquals(-2, v.x);
        assertEquals(1, v.y);
    }

    @Test
    public void testSub2() {
        Vector2D v = new Vector2D(2,-4).sub(4,-5);

        assertEquals(-2, v.x);
        assertEquals(1, v.y);
    }

    @Test
    public void testMult1() {
        Vector2D v = new Vector2D(2,3).mult(new Vector2D(-4,5));

        assertEquals(-8, v.x);
        assertEquals(15, v.y);
    }

    @Test
    public void testMult2() {
        Vector2D v = new Vector2D(2,3).mult(-4,5);

        assertEquals(-8, v.x);
        assertEquals(15, v.y);
    }

    @Test
    public void testMult3() {
        Vector2D v = new Vector2D(2,3).mult(2);

        assertEquals(4, v.x);
        assertEquals(6, v.y);
    }

    @Test
    public void testDot() {
        double dot = new Vector2D(3,1).dot(new Vector2D(-4, 5));

        assertEquals(-7, dot);
    }

    @Test
    public void testLength() {
        double length = new Vector2D(3,4).length();

        assertEquals(5, length);
    }

    @Test
    public void testLengthSquared() {
        double lengthSquared = new Vector2D(3,4).lengthSquared();

        assertEquals(25, lengthSquared);
    }

    @Test
    public void testUnit() {
        Vector2D unit = new Vector2D(2, 2 * Math.sqrt(3)).unit();

        assertEquals(1.0 / 2.0, unit.x, 0.0001f);
        assertEquals(Math.sqrt(3) / 2.0, unit.y, 0.0001f);
    }
}

