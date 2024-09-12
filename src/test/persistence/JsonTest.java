package persistence;

import model.Night;
import model.SleepTracker;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkNight(String date, int duration, int quality, Night night) {
        assertEquals(date, night.getDate());
        assertEquals(duration, night.getDuration());
        assertEquals(quality, night.getQuality());
    }

}