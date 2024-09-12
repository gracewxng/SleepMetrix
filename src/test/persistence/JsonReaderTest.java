package persistence;

import model.Night;
import model.SleepTracker;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            SleepTracker st = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySleepTracker() {
        JsonReader reader = new JsonReader("./data/testWriterEmptySleepTracker.json");
        try {
            SleepTracker st = reader.read();
            assertEquals(0, st.getNumNights());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralSleepTracker() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralSleepTracker.json");
        try {
            SleepTracker st = reader.read();
            List<Night> nights = st.getNights();
            assertEquals(2, nights.size());
            checkNight("11/11/11", 500, 5, nights.get(0));
            checkNight("02/22/22", 600, 7, nights.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
