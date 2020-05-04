package nl.tudelft.context.cg2.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("all")
public class DummyTest {

    @Test
    public void testInc() {
        assertEquals(2, Dummy.inc(1));
    }
}