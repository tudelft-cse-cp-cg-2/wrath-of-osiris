package nl.tudelft.context.cg2.client.model;

import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector3DTest {
    @Test
    public void testEmptyConstructor() {
        Vector3D v = new Vector3D();

        assertEquals(0, v.x);
        assertEquals(0, v.y);
        assertEquals(0, v.z);
    }

    @Test
    public void testAdd1() {
        Vector3D v = new Vector3D(2,3,-1).add(new Vector3D(4,-2,5));

        assertEquals(6, v.x);
        assertEquals(1, v.y);
        assertEquals(4, v.z);
    }

    @Test
    public void testAdd2() {
        Vector3D v = new Vector3D(2,3,-1).add(4,-2,5);

        assertEquals(6, v.x);
        assertEquals(1, v.y);
        assertEquals(4, v.z);
    }

    @Test
    public void testSub1() {
        Vector3D v = new Vector3D(5,-5,5).sub(new Vector3D(3,2,1));

        assertEquals(2, v.x);
        assertEquals(-7, v.y);
        assertEquals(4, v.z);
    }

    @Test
    public void testSub2() {
        Vector3D v = new Vector3D(5,-5,5).sub(3,2,1);

        assertEquals(2, v.x);
        assertEquals(-7, v.y);
        assertEquals(4, v.z);
    }

    @Test
    public void testMult1() {
        Vector3D v = new Vector3D(2,3,4).mult(new Vector3D(5,6,7));

        assertEquals(10, v.x);
        assertEquals(18, v.y);
        assertEquals(28, v.z);
    }

    @Test
    public void testMult2() {
        Vector3D v = new Vector3D(2,3,4).mult(5,6,7);

        assertEquals(10, v.x);
        assertEquals(18, v.y);
        assertEquals(28, v.z);
    }

    @Test
    public void testMult3() {
        Vector3D v = new Vector3D(-1,1,-1).mult(2);

        assertEquals(-2, v.x);
        assertEquals(2, v.y);
        assertEquals(-2, v.z);
    }

    @Test
    public void testDot() {
        double dot = new Vector3D(3,1,2).dot(new Vector3D(-4, 5,-1));

        assertEquals(-9, dot);
    }

    @Test
    public void testLength() {
        double length = new Vector3D(3,4,12).length();

        assertEquals(13, length);
    }

    @Test
    public void testLengthSquared() {
        double lengthSquared = new Vector3D(3,4,12).lengthSquared();

        assertEquals(169, lengthSquared);
    }

    @Test
    public void testUnit() {
        Vector3D unit = new Vector3D(3,4,12).unit();

        assertEquals(3.0 / 13.0, unit.x, 0.0001f);
        assertEquals(4.0 / 13.0, unit.y, 0.0001f);
        assertEquals(12.0 / 13.0, unit.z, 0.0001f);
    }
}

