package ui;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SleepTrackerGUI().setVisible(true);
        });
    }
}