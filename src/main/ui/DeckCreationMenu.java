package ui;

import model.Deck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// A menu designed to be contained within a CardAppGraphical that allows a user to create new decks
public class DeckCreationMenu extends JPanel implements ActionListener {

    private ArrayList<Deck> decks;
    private final CardAppGraphical cardAppGraphical;
    private final DeckDisplayMenu deckDisplayMenu;
    private final JTextField name;

    // EFFECTS: Constructs the DeckCreationMenu
    public DeckCreationMenu(CardAppGraphical cardAppGraphical) {
        this.decks = cardAppGraphical.getCardCollection().getDecks();
        this.cardAppGraphical = cardAppGraphical;
        this.deckDisplayMenu = cardAppGraphical.getDeckDisplayMenu();
        JPanel dataEntry = new JPanel();
        dataEntry.setLayout(new FlowLayout());
        dataEntry.add(new JLabel("Deck name:"));
        name = new JTextField(30);
        dataEntry.add(name);
        this.setLayout(new FlowLayout());
        this.add(new JLabel("Deck Creator", SwingConstants.CENTER));
        this.add(dataEntry);
        JButton submitButton = new JButton("Create Deck!");
        submitButton.setActionCommand("submit");
        submitButton.addActionListener(this);
        this.add(submitButton);
    }

    // MODIFIES: cardAppGraphical, decks
    // EFFECTS: Creates a new deck satisfying the data provided in the JTextFields. Then, the input in the fields
    //          is cleared and the cardDisplayMenu is reset to display the changes.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("submit")) {
            if (name != null && name.getText() != null) {
                Deck deck = new Deck(name.getText());
                decks.add(deck);
                JButton button = new JButton(name.getText());
                button.setActionCommand(name.getText());
                button.addActionListener(e1 -> {
                    cardAppGraphical.resetDeckCardDisplayMenu(deck);
                    cardAppGraphical.setSelectedDeck(deck);
                });
                deckDisplayMenu.getDeckButtons().add(button);
                name.setText("");
            }
            cardAppGraphical.revalidate();
            cardAppGraphical.repaint();
        }
    }

    public void setDecks(ArrayList<Deck> decks) {
        this.decks = decks;
    }
}
