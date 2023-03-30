package ui;

import javax.swing.*;
import java.awt.*;

// A visual representation of the rarity of a card (Common=Green, Uncommon=Blue, Rare=Red, Mythic=Orange)
public class RarityPanel extends JPanel {
    private final Color fillColor;

    // EFFECTS: Constructs the RarityPanel
    public RarityPanel(String rarity) {
        switch (rarity) {
            case "Common":
                fillColor = Color.green;
                break;
            case "Uncommon":
                fillColor = Color.blue;
                break;
            case "Rare":
                fillColor = Color.red;
                break;
            default:
                fillColor = Color.orange;
                break;
        }
    }

    // EFFECTS: Displays a hollow square with its border color defined by fillColor
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(fillColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.white);
        g.fillRect(3, 3, getWidth() - 6, getHeight() - 6);
    }

}
