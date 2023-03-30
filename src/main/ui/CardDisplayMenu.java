package ui;

import model.Card;
import model.Deck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// A menu that displays the names of cards held within a specified deck as buttons that can be
// clicked to select that card
public class CardDisplayMenu extends JPanel {
    private JPanel cardButtons;
    private final CardAppGraphical cardAppGraphical;

    // EFFECTS: Constructs the CardDisplayMenu
    public CardDisplayMenu(Deck cards, CardAppGraphical cardAppGraphical) {
        this.setPreferredSize(new Dimension(400,200));
        this.cardAppGraphical = cardAppGraphical;
        cardButtons = new JPanel();
        cardButtons.setLayout(new FlowLayout());
        if (cards == null) {
            this.setLayout(new FlowLayout());
            this.add(new JLabel("Cards in null: "), BorderLayout.NORTH);
        } else {
            for (Card c : cards.getCards()) {
                JButton button = new JButton(c.getCardName());
                button.setActionCommand(c.getCardName());
                button.addActionListener(e -> cardAppGraphical.resetCardDetailDisplayMenu(c));
                cardButtons.add(button);
            }
            this.setLayout(new FlowLayout());
            this.add(new JLabel("Cards in " + cards.getDeckName() + ": "), BorderLayout.NORTH);
            this.add(cardButtons);
        }
    }

    // MODIFIES: this, cardGraphicalApp
    // EFFECTS: Clears all components from this, before recreating them with a new Deck provided
    public void reset(Deck cards) {
        cardButtons = new JPanel();
        cardButtons.setLayout(new FlowLayout());
        this.setLayout(new FlowLayout());
        if (cards == null) {
            removeAll();
            this.add(new JLabel("Cards in null: "), BorderLayout.NORTH);
        } else {
            for (Card c : cards.getCards()) {
                JButton button = new JButton(c.getCardName());
                button.setActionCommand(c.getCardName());
                button.addActionListener(e -> cardAppGraphical.resetCardDetailDisplayMenu(c));
                cardButtons.add(button);
            }
            removeAll();
            this.add(new JLabel("Cards in " + cards.getDeckName() + ": "), BorderLayout.NORTH);
            this.add(cardButtons);
        }
    }
}
