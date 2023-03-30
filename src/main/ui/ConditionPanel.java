package ui;

import javax.swing.*;
import java.awt.*;

// A visual representation of the condition of a card (Bright pink = Good Condition, Grey/Black = Bad Condition)
public class ConditionPanel extends JPanel {
    private final Color fillColor;

    // EFFECTS: Constructs the ConditionPanel
    public ConditionPanel(double condition) {
        fillColor = new Color((int)(25.5 * condition),(int)(12.7 * condition),(int)(25.5 * condition));
    }

    // EFFECTS: Overrides the paint command to draw a small coloured square border with the color defined by fillColor
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(fillColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.white);
        g.fillRect(3, 3, getWidth() - 6, getHeight() - 6);
    }

}
