package ui;

import model.Card;
import model.Deck;

import javax.swing.*;
import java.awt.*;

// A menu designed to display the relevant data of a card as well as buttons for deck manipulation (add/remove cards)
public class CardDetailDisplayMenu extends JPanel {
    private final CardAppGraphical cardAppGraphical;
    private ConditionPanel conditionPanel;
    private RarityPanel rarityPanel;

    // EFFECTS: Constructs the CardDetailDisplayMenu
    public CardDetailDisplayMenu(Card card, CardAppGraphical cardAppGraphical) {
        this.cardAppGraphical = cardAppGraphical;
        if (card == null) {
            this.setLayout(new FlowLayout());
            this.add(new JLabel("No Card Selected"), BorderLayout.CENTER);
        } else {
            this.setLayout(new BorderLayout());
            add(cardDetail(card),BorderLayout.NORTH);
            add(addButton(card));
            add(removeButton(card));
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes all the components from the class before reinitializing them with a new Card
    public void reset(Card card) {
        removeAll();
        if (card == null) {
            this.setLayout(new FlowLayout());
            this.add(new JLabel("No Card Selected"), BorderLayout.CENTER);
        } else {
            this.setLayout(new FlowLayout());
            add(cardDetail(card),BorderLayout.NORTH);
            add(addButton(card));
            add(removeButton(card));
        }
    }

    // REQUIRES: card != null
    // EFFECTS: Creates a button that adds the current selected card to the current selected deck on press if not null
    public JButton addButton(Card card) {
        JButton button = new JButton("Add to current deck");
        button.setActionCommand("add");
        button.addActionListener(e -> {
            Deck deck = cardAppGraphical.getSelectedDeck();
            if (deck != null) {
                deck.add(card);
                cardAppGraphical.resetDeckCardDisplayMenu(deck);
            }
        });
        return button;
    }

    // REQUIRES: card != null
    // EFFECTS: Creates a button that removes the selected card from the selected deck on press if not null
    public JButton removeButton(Card card) {
        JButton button = new JButton("Remove from current deck");
        button.setActionCommand("remove");
        button.addActionListener(e -> {
            Deck deck = cardAppGraphical.getSelectedDeck();
            if (deck != null) {
                deck.remove(card);
                cardAppGraphical.resetDeckCardDisplayMenu(deck);
            }
        });
        return button;
    }

    // REQUIRES: card != null
    // EFFECTS: Constructs and returns a JPanel containing relevant information to the selected card
    public JPanel cardDetail(Card card) {
        JPanel output = new JPanel();
        output.setLayout(new FlowLayout());
        output.add(new JLabel(card.getCardName()), BorderLayout.NORTH);
        rarityPanel = new RarityPanel(card.getRarity());
        output.add(rarityPanel);
        conditionPanel = new ConditionPanel(card.getCondition());
        output.add(conditionPanel);
        output.add(new JLabel(Integer.toString(card.getManaCost())));
        output.add(new JLabel(card.getCardType()));
        return output;
    }
}
