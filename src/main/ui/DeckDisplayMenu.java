package ui;

import model.Deck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// A menu that displays the names of decks held within a specified list of decks as buttons that can be
// clicked to select that deck
public class DeckDisplayMenu extends JPanel {
    private final CardAppGraphical cardAppGraphical;
    private JPanel deckButtons;

    // EFFECTS: Constructs a DeckDisplayMenu
    public DeckDisplayMenu(ArrayList<Deck> decks, CardAppGraphical cardAppGraphical) {
        this.cardAppGraphical = cardAppGraphical;
        deckButtons = new JPanel();
        deckButtons.setLayout(new FlowLayout());
        for (Deck d : decks) {
            JButton button = new JButton(d.getDeckName());
            button.setActionCommand(d.getDeckName());
            button.addActionListener(e -> {
                cardAppGraphical.resetDeckCardDisplayMenu(d);
                cardAppGraphical.setSelectedDeck(d);
            });
            deckButtons.add(button);
        }
        this.setLayout(new FlowLayout());
        this.add(new JLabel("Decks:"), BorderLayout.NORTH);
        this.add(deckButtons);
    }

    // MODIFIES: this, cardGraphicalApp
    // EFFECTS: Clears all components from this, before recreating them with a new ArrayList<Deck> provided
    public void reset(ArrayList<Deck> decks) {
        deckButtons = new JPanel();
        deckButtons.setLayout(new FlowLayout());
        for (Deck d : decks) {
            JButton button = new JButton(d.getDeckName());
            button.setActionCommand(d.getDeckName());
            button.addActionListener(e -> {
                cardAppGraphical.resetDeckCardDisplayMenu(d);
                cardAppGraphical.setSelectedDeck(d);
            });
            deckButtons.add(button);
        }
        removeAll();
        this.setLayout(new FlowLayout());
        this.add(new JLabel("Decks:"), BorderLayout.NORTH);
        this.add(deckButtons);
    }

    public JPanel getDeckButtons() {
        return deckButtons;
    }
}
