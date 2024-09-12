package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a night's sleep with date, duration (in minutes), and quality (1-10)
public class Night implements Writable {
    private String date; // the date of the night's sleep
    private int duration; // duration of sleep in minutes
    private int quality; // quality of sleep on 1-10 scale

    // REQUIRES: date != null, duration > 0, 1 <= quality <= 10
    // MODIFIES: this
    // EFFECTS: constructs a nigh with given date, duration, and quality
    public Night(String date, int duration, int quality) {
        this.date = date;
        this.duration = duration;
        this.quality = quality;
    }

    // EFFECTS: returns date of the night
    public String getDate() {
        return date;
    }

    // REQUIRES: date != null
    // MODIFIES: this
    // EFFECTS: sets the date of the night's sleep
    public void setDate(String date) {
        this.date = date;
    }

    // EFFECTS: returns the duration of the sleep in minutes
    public int getDuration() {
        return duration;
    }

    // REQUIRES: duration > 0
    // MODIFIES: this
    // EFFECTS: sets the duration of sleep in minutes
    public void setDuration(int duration) {
        this.duration = duration;
    }

    // EFFECTS: returns quality of sleep on 1-10 scale
    public int getQuality() {
        return quality;
    }

    // REQUIRES: 1 <= quality <= 10
    // MODIFIES: this
    // EFFECTS: sets the quality of sleep on 1-10 scale
    public void setQuality(int quality) {
        this.quality = quality;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", date);
        json.put("duration", duration);
        json.put("quality",quality);
        return json;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Duration: " + duration + " minutes, Quality: " + quality + "/10";
    }
}
