package ui;

import model.Night;

import javax.swing.*;
import java.awt.*;

// Custom cell renderer for rendering Night objects in JList
public class NightCellRenderer extends DefaultListCellRenderer {

    // Configure rendering for Night objects in JList
    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        Night night = (Night) value;
        String date = night.getDate();
        int duration = night.getDuration();
        int quality = night.getQuality();
        String nightText = "<html>Date: " + date + "<br/>Duration: " + duration + " minutes<br/>Quality: " + quality;
        setText(nightText);

        return this;
    }
}
