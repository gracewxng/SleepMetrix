package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

// Tests for SleepTracker and Nights
class SleepTrackerTest {
    private SleepTracker testSleepTracker;
    private Night night1;
    private Night night2;

    @BeforeEach
    void setUp() {
        testSleepTracker = new SleepTracker();
        night1 = new Night("02/17/24", 360, 6);
        night2 = new Night("02/18/24", 480, 8);
    }

    @Test
    void testAddNight() {
        assertEquals(0, testSleepTracker.getNights().size());
        testSleepTracker.addNight(night1);
        assertEquals(1, testSleepTracker.getNights().size());
    }

    @Test
    void testRemoveNight() {
        assertEquals(0, testSleepTracker.getNights().size());
        testSleepTracker.addNight(night1);
        assertEquals(1, testSleepTracker.getNights().size());
        testSleepTracker.removeNight(night1);
        assertEquals(0, testSleepTracker.getNights().size());
    }

    @Test
    void testCalculateAverageSleepDuration() {
        assertEquals(0, testSleepTracker.calculateAverageSleepDuration());
        testSleepTracker.addNight(night1);
        assertEquals(360, testSleepTracker.calculateAverageSleepDuration());
        testSleepTracker.addNight(night2);
        assertEquals(420, testSleepTracker.calculateAverageSleepDuration());
    }

    @Test
    void testCalculateTotalSleepDuration() {
        assertEquals(0, testSleepTracker.calculateTotalSleepDuration());
        testSleepTracker.addNight(night1);
        assertEquals(360, testSleepTracker.calculateTotalSleepDuration());
        testSleepTracker.addNight(night2);
        assertEquals(840, testSleepTracker.calculateTotalSleepDuration());
    }

    @Test
    void testGetNights() {
        testSleepTracker.addNight(night1);
        assertEquals(1, testSleepTracker.getNights().size());
        assertTrue(testSleepTracker.getNights().contains(night1));
    }

    @Test
    void testNightGettersAndSetters() {
        assertEquals("02/17/24", night1.getDate());
        assertEquals(360, night1.getDuration());
        assertEquals(6, night1.getQuality());

        night1.setDate("02/22/24");
        assertEquals("02/22/24", night1.getDate());

        night1.setDuration(420);
        assertEquals(420, night1.getDuration());

        night1.setQuality(9);
        assertEquals(9, night1.getQuality());
    }

    @Test
    void testNightToString() {
        assertEquals("Date: 02/17/24, Duration: 360 minutes, Quality: 6/10", night1.toString());
        assertEquals("Date: 02/18/24, Duration: 480 minutes, Quality: 8/10", night2.toString());
    }
}