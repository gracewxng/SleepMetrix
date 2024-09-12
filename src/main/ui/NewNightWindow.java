package ui;

import javax.swing.*;
import java.awt.*;

// Represents the new night creation popup window
public class NewNightWindow {
    private JFrame frame;
    private JPanel panel;
    private JTextField durationField;
    private JTextField dateField;
    private JTextField qualityField;
    private JLabel durationLabel;
    private JLabel dateLabel;
    private JLabel qualityLabel;

    // EFFECTS: Creates a new night creation window
    public NewNightWindow() {
        initializeFields();

        panel.add(durationLabel);
        panel.add(durationField);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(qualityLabel);
        panel.add(qualityField);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setMinimumSize(new Dimension(250, 200));
        panel.setPreferredSize(new Dimension(250, 200));
    }

    // EFFECTS: Initializes night creation fields
    private void initializeFields() {
        frame = new JFrame("New Night");
        panel = new JPanel();

        ImageIcon nightImage = new ImageIcon("./data/nightImage.jpg");

        durationLabel = new JLabel("Duration of Sleep (in minutes): ");
        durationField = new JTextField();
        durationField.setMaximumSize(new Dimension(300, 25));

        dateLabel = new JLabel("Date of Sleep (MM/DD/YY): ");
        dateField = new JTextField();
        dateField.setMaximumSize(new Dimension(300, 25));

        qualityLabel = new JLabel("Quality of Sleep (1-10): ");
        qualityField = new JTextField();
        qualityField.setMaximumSize(new Dimension(300, 25));
    }

    public JPanel returnJPanel() {
        return panel;
    }

    // EFFECTS: Retrieves duration of sleep entered
    public int getDuration() {
        return Integer.parseInt(durationField.getText());
    }

    // EFFECTS: Retrieves date of sleep entered
    public String getDate() {
        return dateField.getText();
    }

    // EFFECTS: Retrieves quality of sleep entered
    public int getQuality() {
        return Integer.parseInt(qualityField.getText());
    }
}
