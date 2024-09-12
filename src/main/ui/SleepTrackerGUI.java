package ui;

import model.Event;
import model.EventLog;
import model.Night;
import model.SleepTracker;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.List;

// Represents the graphical user interface for the Sleep Tracker application
public class SleepTrackerGUI extends JFrame implements ListSelectionListener {
    private static final String JSON_STORE = "./data/sleep_log.json";
    private static final int WIDTH = 2000;
    private static final int HEIGHT = 1000;

    private DefaultListModel<Night> nightListModel;

    private JList<Night> nightLogJList;
    private JLabel totalHoursLabel;

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private SleepTracker sleepTracker;

    // EFFECTS: Initializes the SleepTrackerGUI
    public SleepTrackerGUI() {
        super("Sleep Tracker");
        initializeUI();
        initializeData();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveSleepTracker();
            }
        });

        loadSleepTracker();
    }

    // MODIFIES: this
    // EFFECTS: Initializes the user interface components
    private void initializeUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalHoursLabel = new JLabel("Total Hours Slept: ");
        totalHoursLabel.setFont(new Font("Quicksand", Font.BOLD, 20));
        headerPanel.add(totalHoursLabel);
        headerPanel.setBackground(new Color(239, 230, 252));
        add(headerPanel, BorderLayout.NORTH);

        nightListModel = new DefaultListModel<>();
        nightLogJList = new JList<>(nightListModel);
        nightLogJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nightLogJList.setFont(new Font("Quicksand", Font.PLAIN, 14));
        nightLogJList.addListSelectionListener(this);

        JScrollPane scrollPane = new JScrollPane(nightLogJList);
        scrollPane.setPreferredSize(new Dimension(300, 300));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(239, 230, 252));
        addButtons(buttonPanel);
    }

    // MODIFIES: buttonPanel
// EFFECTS: Adds buttons to the button panel
    private void addButtons(JPanel buttonPanel) {
        addButton(buttonPanel, "Add Night", e -> addNight());
        addButton(buttonPanel, "Remove Night", e -> removeNight());
        addButton(buttonPanel, "View All Nights", e -> viewAllNights());
        addButton(buttonPanel, "View Statistics", e -> viewStatistics());
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: panel
    // EFFECTS: Adds a button to the specified panel with the given label and action listener
    private void addButton(JPanel panel, String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        button.setPreferredSize(new Dimension(150, 40));
        button.setFont(new Font("Quicksand", Font.PLAIN, 16));
        button.setBackground(new Color(239, 230, 252));
        panel.add(button);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the sleep tracker data
    private void initializeData() {
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        try {
            sleepTracker = jsonReader.read();
            updateUI();
        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_STORE);
            sleepTracker = new SleepTracker();
        }
    }

    // MODIFIES: this
    // EFFECTS: Updates the user interface with the current sleep tracker data
    private void updateUI() {
        nightListModel.clear();
        List<Night> nights = sleepTracker.getNights();
        for (Night night : nights) {
            nightListModel.addElement(night);
        }
        int totalHours = sleepTracker.calculateTotalSleepDuration();
        totalHoursLabel.setText("Total Hours Slept: " + totalHours);
    }

    // MODIFIES: this
    // EFFECTS: Adds a new night to the sleep tracker
    private void addNight() {
        NewNightWindow newNightWindow = new NewNightWindow();
        JPanel panel = newNightWindow.returnJPanel();
        int optionPaneValue = JOptionPane.showConfirmDialog(
                null, panel, "Add New Night",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                getScaledImageIcon("./data/logo.png", 150, 150));

        if (optionPaneValue == JOptionPane.OK_OPTION) {
            try {
                int duration = newNightWindow.getDuration();
                int quality = newNightWindow.getQuality();
                String date = newNightWindow.getDate();
                Night night = new Night(date, duration, quality);
                sleepTracker.addNight(night);
                jsonWriter.open();
                jsonWriter.write(sleepTracker);
                jsonWriter.close();
                updateUI();
            } catch (NumberFormatException | IOException e) {
                JOptionPane.showMessageDialog(null, "Invalid input! Please enter valid numbers.",
                        "Input Error", JOptionPane.ERROR_MESSAGE,
                        getScaledImageIcon("./data/logo.png", 150, 150));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes the selected night from the sleep tracker
    private void removeNight() {
        int selectedIndex = nightLogJList.getSelectedIndex();
        if (selectedIndex != -1) {
            Night nightToRemove = nightListModel.getElementAt(selectedIndex);
            sleepTracker.removeNight(nightToRemove);
            try {
                jsonWriter.open();
                jsonWriter.write(sleepTracker);
                jsonWriter.close();
                updateUI();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error: Unable to write to file " + JSON_STORE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a night to remove.",
                    "No Night Selected", JOptionPane.WARNING_MESSAGE,
                    getScaledImageIcon("./data/logo.png", 150, 150));
        }
    }

    // MODIFIES: none
    // EFFECTS: Displays a dialog with details of all recorded nights
    private void viewAllNights() {
        StringBuilder nightDetails = new StringBuilder();
        List<Night> nights = sleepTracker.getNights();
        if (!nights.isEmpty()) {
            for (Night night : nights) {
                nightDetails.append("Date: ").append(night.getDate()).append(", Duration: ")
                        .append(night.getDuration()).append(" minutes, Quality: ")
                        .append(night.getQuality()).append("/10\n");
            }
            JTextArea textArea = new JTextArea(nightDetails.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(300, 200));
            JOptionPane.showMessageDialog(null, scrollPane, "All Nights Recorded",
                    JOptionPane.INFORMATION_MESSAGE,
                    getScaledImageIcon("./data/logo.png", 150, 150));
        } else {
            JOptionPane.showMessageDialog(null, "No nights recorded yet :(",
                    "All Nights Recorded", JOptionPane.INFORMATION_MESSAGE,
                    getScaledImageIcon("./data/logo.png", 150, 150));
        }
    }

    // MODIFIES: none
    // EFFECTS: Displays a dialog with sleep statistics
    private void viewStatistics() {
        int totalNights = sleepTracker.getNights().size();
        int totalDuration = sleepTracker.calculateTotalSleepDuration();
        int averageDuration = sleepTracker.calculateAverageSleepDuration();
        String message = String.format("Total Nights: %d\nTotal Sleep Duration: %d minutes\nAverage Sleep Duration:"
                        + "%d minutes",
                totalNights, totalDuration, averageDuration);
        JOptionPane.showMessageDialog(null, message, "Sleep Statistics",
                JOptionPane.INFORMATION_MESSAGE, getScaledImageIcon("./data/logo.png", 150, 150));
    }

    private void loadSleepTracker() {
        int response = JOptionPane.showConfirmDialog(null,
                "Would you like to load your saved sleep log?", "Load Sleep Log",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                getScaledImageIcon("./data/logo.png", 150, 150));
        if (response == JOptionPane.YES_OPTION) {
            try {
                sleepTracker = jsonReader.read();
                updateUI();
                System.out.println("Sleep log loaded successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error: Unable to load sleep log.",
                        "Load Error", JOptionPane.ERROR_MESSAGE,
                        getScaledImageIcon("./data/logo.png", 150, 150));
            }
        }
    }


    private void saveSleepTracker() {
        int response = JOptionPane.showConfirmDialog(null,
                "Would you like to save your sleep log before closing?", "Save Sleep Log",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                getScaledImageIcon("./data/logo.png", 150, 150));
        if (response == JOptionPane.YES_OPTION) {
            try {
                jsonWriter.open();
                jsonWriter.write(sleepTracker);
                jsonWriter.close();
                System.out.println("Sleep log saved successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error: Unable to save sleep log.",
                        "Save Error", JOptionPane.ERROR_MESSAGE,
                        getScaledImageIcon("./data/logo.png", 150, 150));
            }
        }

        printEventLog();
    }

    private void printEventLog() {
        EventLog eventLog = EventLog.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        for (Event event : eventLog) {
            stringBuilder.append(event.toString()).append("\n");
        }
        System.out.println("Event Log:");
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // Not needed
    }

    private ImageIcon getScaledImageIcon(String path, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(path);
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}