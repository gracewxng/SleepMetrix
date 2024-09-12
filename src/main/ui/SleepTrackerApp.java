package ui;

import model.Event;
import model.EventLog;

import model.Night;
import model.SleepTracker;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Iterator;
import java.util.Scanner;
import java.util.List;

// Runs and implements SleepTracker
public class SleepTrackerApp {
    private static final String JSON_STORE = "./data/SleepTracker.json";
    private SleepTracker sleepTracker;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: constructs SleepTrackerApp object and initializes sleep tracker
    public SleepTrackerApp() {
        input = new Scanner(System.in);
        sleepTracker = new SleepTracker();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runSleepTracker();
    }

    // MODIFIES: this
    // EFFECTS: runs sleep tracker application until user chooses to quit
    public void runSleepTracker() {
        boolean keepRunning = true;
        String command = null;

        initialize();

        while (keepRunning) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                keepRunning = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Goodbye! Sleep well :)");
    }

    // REQUIRES: command != null
    // MODIFIES: this
    // EFFECTS: processes user command and performs corresponding action
    public void processCommand(String command) {
        if (command.equals("add")) {
            doAddNight();
        } else if (command.equals("remove")) {
            doRemoveNight();
        } else if (command.equals("stats")) {
            doDisplayStats();
        } else if (command.equals("view")) {
            doListNights();
        } else if (command.equals("save")) {
            saveSleepTracker();
        } else if (command.equals("load")) {
            loadSleepTracker();
        } else {
            System.out.println("Invalid selection... Please try again!");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes scanner and sleep tracker objects
    public void initialize() {
        input = new Scanner(System.in);
        sleepTracker = new SleepTracker();
    }

    // MODIFIES: console output
    // EFFECTS: displays menu options to the user
    public void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tadd -> add night");
        System.out.println("\tremove -> remove night");
        System.out.println("\tstats -> see sleep stats");
        System.out.println("\tview -> view all nights recorded");
        System.out.println("\tsave -> save nights to file");
        System.out.println("\tload -> load nights from file");
        System.out.println("\tquit -> quit application");
        System.out.println("\nYour choice: ");
    }

    // MODIFIES: this
    // EFFECTS: prompts  user to enter details of a new night and adds it to the sleep tracker
    public void doAddNight() {
        System.out.print("Enter sleep duration in minutes: ");
        int duration = input.nextInt();
        input.nextLine();

        System.out.print("Enter date (MM/DD/YY): ");
        String date = input.nextLine().trim();

        System.out.print("Enter sleep quality (1-10): ");
        int quality = input.nextInt();
        input.nextLine();

        Night night = new Night(date, duration, quality);
        sleepTracker.addNight(night);
        System.out.println("Night added successfully!");
    }

    // MODIFIES: this
    // EFFECTS: prompts user to enter the date of the night to be removed,
    //          removes the night from sleep tracker if found, and prints a success message,
    //          otherwise, prints a message indicating the night was not found
    public void doRemoveNight() {
        System.out.print("Enter date of the night to remove (MM/DD/YY): ");
        String date = input.nextLine().trim();
        input.nextLine();
        Night nightToRemove = null;
        for (Night night : sleepTracker.getNights()) {
            if (night.getDate().equals(date)) {
                nightToRemove = night;
            }
        }
        if (nightToRemove != null) {
            sleepTracker.removeNight(nightToRemove);
            System.out.println("Night removed successfully!");
        } else {
            System.out.println("Night not found.");
        }
    }

    // MODIFIES: console output
    // EFFECTS: prints sleep statistics including total number of nights recorded,
    //          average sleep duration, and total sleep duration
    public void doDisplayStats() {
        if (sleepTracker.getNights().isEmpty()) {
            System.out.println("No nights recorded yet :(");
        } else {
            System.out.println("\nSleep Statistics:");
            System.out.println("Total nights recorded: " + sleepTracker.getNights().size());
            System.out.println("Average sleep duration: " + sleepTracker.calculateAverageSleepDuration() + " minutes");
            System.out.println("Total sleep duration: " + sleepTracker.calculateTotalSleepDuration() + " minutes");
        }
    }

    // MODIFIES: console output
    // EFFECTS: prints details of all nights recorded including date, duration, and quality
    public void doListNights() {
        List<Night> nights = sleepTracker.getNights();
        if (nights.isEmpty()) {
            System.out.println("No nights recorded yet :(");
        } else {
            System.out.println("\nAll Nights Recorded:");
            for (int i = 0; i < nights.size(); i++) {
                Night night = nights.get(i);
                System.out.println("Night " + (i + 1) + ":");
                System.out.println("\tDate: " + night.getDate());
                System.out.println("\tDuration: " + night.getDuration() + " minutes");
                System.out.println("\tQuality: " + night.getQuality() + "/10");
            }
        }
    }

    // EFFECTS: saves the nights to file
    private void saveSleepTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(sleepTracker);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads nights from file
    private void loadSleepTracker() {
        try {
            sleepTracker = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}