package persistence;

import model.Night;
import model.SleepTracker;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            SleepTracker st = new SleepTracker();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySleepTracker() {
        try {
            SleepTracker st = new SleepTracker();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySleepTracker.json");
            writer.open();
            writer.write(st);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySleepTracker.json");
            st = reader.read();
            assertEquals(0, st.getNumNights());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralSleepTracker() {
        try {
            SleepTracker st = new SleepTracker();
            st.addNight(new Night("11/11/11", 500, 5));
            st.addNight(new Night("02/22/22", 600, 7));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSleepTracker.json");
            writer.open();
            writer.write(st);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSleepTracker.json");
            st = reader.read();
            List<Night> nights = st.getNights();
            assertEquals(2, nights.size());
            checkNight("11/11/11", 500, 5, nights.get(0));
            checkNight("02/22/22", 600, 7, nights.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
