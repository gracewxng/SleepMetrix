package persistence;

import model.Night;
import model.SleepTracker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads SleepTracker from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads SleepTracker from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SleepTracker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSleepTracker(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses SleepTracker from JSON object and returns it
    private SleepTracker parseSleepTracker(JSONObject jsonObject) {
        SleepTracker st = new SleepTracker();
        addNights(st, jsonObject);
        return st;
    }

    // MODIFIES: st
    // EFFECTS: parses Night from JSON object and adds them to SleepTracker
    private void addNights(SleepTracker st, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("nights");
        for (Object json : jsonArray) {
            JSONObject nextNight = (JSONObject) json;
            addNight(st, nextNight);
        }
    }

    // MODIFIES: st
    // EFFECTS: parses Night from JSON object and adds it to SleepTracker
    private void addNight(SleepTracker st, JSONObject jsonObject) {
        String date = jsonObject.getString("date");
        int duration = jsonObject.getInt("duration");
        int quality = jsonObject.getInt("quality");
        Night night = new Night(date, duration, quality);
        st.addNight(night);
    }
}
