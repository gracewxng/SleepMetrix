package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a sleep tracker that keeps track of nights slept
public class SleepTracker implements Writable {
    private List<Night> nights;
    private EventLog eventLog;

    // MODIFIES: this
    // EFFECTS: constructs an empty SleepTracker object
    public SleepTracker() {
        nights = new ArrayList<>();
    }

    // REQUIRES: night != null
    // MODIFIES: this
    // EFFECTS: adds given night to the list of nights
    public void addNight(Night night) {
        nights.add(night);

        EventLog.getInstance().logEvent(new Event("Added Night: " + night.getDate()));
    }

    // REQUIRES: night != null
    // MODIFIES: this
    // EFFECTS: removes given night from the list of nights
    public void removeNight(Night night) {
        nights.remove(night);

        EventLog.getInstance().logEvent(new Event("Removed Night: " + night.getDate()));
    }

    // EFFECTS: returns average sleep duration of all nights slept
    public int calculateAverageSleepDuration() {
        if (nights.isEmpty()) {
            return 0;
        } else {
            int totalDuration = 0;

            for (Night night : nights) {
                totalDuration += night.getDuration();
            }

            return totalDuration / nights.size();
        }
    }

    public int calculateTotalSleepDuration() {
        if (nights.isEmpty()) {
            return 0;
        } else {
            int totalDuration = 0;

            for (Night night : nights) {
                totalDuration += night.getDuration();
            }

            return totalDuration;
        }
    }

    // EFFECTS: returns list of nights
    public List<Night> getNights() {
        return nights;
    }

    // EFFECTS: returns number of Nights in SleepTracker
    public int getNumNights() {
        return nights.size();
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("nights", nightsToJson());
        return json;
    }

    public JSONArray nightsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Night n : nights) {
            jsonArray.put(n.toJson());
        }

        return jsonArray;
    }
}
