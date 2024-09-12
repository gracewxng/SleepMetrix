package model;

import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    private Event e1;
    private Event e2;

    @BeforeEach
    public void setUp() {
        e1 = new Event("Test");
        e2 = new Event("Test");
    }

    @Test
    public void testEvent() {
        assertEquals("Test", e1.getDescription());
        assertEquals(e1.getDate(), e2.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(e1.getDate().toString() + "\n" + "Test", e1.toString());
    }

    @Test
    public void testEqualsSameObject() {
        assertTrue(e1.equals(e1));
    }

    @Test
    public void testEqualsEqualEvents() {
        assertTrue(e1.equals(e2));
    }

    @Test
    public void testEqualsDifferentEvents() {
        e2 = new Event("Different Test");
        assertFalse(e1.equals(e2));
    }

    @Test
    public void testEqualsNull() {
        assertFalse(e1.equals(null));
    }

    @Test
    public void testEqualsDifferentClass() {
        assertFalse(e1.equals("Test"));
    }

    @Test
    public void testHashCodeConsistency() {
        assertEquals(e1.hashCode(), e1.hashCode());
    }

    @Test
    public void testHashCodeEquality() {
        assertEquals(e1.hashCode(), e2.hashCode());
    }
}
