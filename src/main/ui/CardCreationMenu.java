package ui;

import model.Card;
import model.Deck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// A menu designed to be contained within a CardAppGraphical that allows a user to create new cards
public class CardCreationMenu extends JPanel implements ActionListener {

    private Deck allCards;
    private final CardAppGraphical cardAppGraphical;
    private JTextField name;
    private JTextField rarity;
    private JTextField condition;
    private JTextField manaCost;
    private JTextField cardType;

    // EFFECTS: Constructs the CardCreationMenu
    public CardCreationMenu(CardAppGraphical cardAppGraphical) {
        this.allCards = cardAppGraphical.getCardCollection().getAllCards();
        this.cardAppGraphical = cardAppGraphical;
        CardDisplayMenu cardDisplayMenu = cardAppGraphical.getCardDisplayMenu();
        this.setLayout(new FlowLayout());
        this.add(dataEntryFields());
        JButton submitButton = new JButton("Create Card!");
        submitButton.setActionCommand("submit");
        submitButton.addActionListener(this);
        this.add(submitButton);
    }

    // EFFECTS: Constructs and returns a JPanel containing a JTextField for all the necessary fields to create a card
    //          and a JLabel to denote each one.
    public JPanel dataEntryFields() {
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new GridLayout(10,1));
        name = new JTextField(30);
        rarity = new JTextField(30);
        condition = new JTextField(30);
        manaCost = new JTextField(30);
        cardType = new JTextField(30);
        jpanel.add(new JLabel("Name:"));
        jpanel.add(name);
        jpanel.add(new JLabel("Rarity:"));
        jpanel.add(rarity);
        jpanel.add(new JLabel("Condition:"));
        jpanel.add(condition);
        jpanel.add(new JLabel("Mana Cost:"));
        jpanel.add(manaCost);
        jpanel.add(new JLabel("Card Type:"));
        jpanel.add(cardType);
        return jpanel;
    }

    // MODIFIES: cardAppGraphical, this
    // EFFECTS: Creates a new card satisfying the data provided in the JTextFields if possible, else creates nothing.
    //          Then, the input in the fields is cleared regardless. If a card was created it is then added
    //          to allCards then the cardDisplayMenu is reset to display the changes.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("submit")) {
            Card card = createNewCard();
            clearInput();
            if (card != null) {
                allCards.add(card);
                cardAppGraphical.resetCardDisplayMenu(allCards);
            }
        }
    }

    // EFFECTS: Constructs and returns a new card based on the JTextFields if possible, else returns null.
    private Card createNewCard() {
        String nameVal = capitalizeFirst(name.getText());
        String rarityVal = pullRarity();
        double conditionVal = pullCondition();
        int manaCostVal = pullMana();
        String cardTypeVal = capitalizeFirst(cardType.getText());
        if (rarityVal == null || conditionVal == -2.0 || manaCostVal == -1) {
            return null;
        }
        return new Card(nameVal, rarityVal, conditionVal, manaCostVal, cardTypeVal);
    }

    // MODIFIES: this
    // EFFECTS: returns rarity if the user inputs a valid value, else null
    private String pullRarity() {
        String rarVal = capitalizeFirst(rarity.getText());
        if (rarVal.equals("Common") || rarVal.equals("Uncommon") || rarVal.equals("Rare") || rarVal.equals("Mythic")) {
            return rarVal;
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: returns condition if the user inputs a valid value, else -2.0
    private double pullCondition() {
        double conditionVal;
        try {
            conditionVal = Double.parseDouble(condition.getText());
        } catch (Exception e) {
            conditionVal = -2.0;
        }
        if (!((conditionVal <= 10 && conditionVal >= 0) || conditionVal == -1)) {
            conditionVal = -2.0;
        }
        return conditionVal;
    }

    // MODIFIES: this
    // EFFECTS: returns mana cost if the user inputs a valid value, else -1
    private int pullMana() {
        int manaVal;
        try {
            manaVal = Integer.parseInt(manaCost.getText());
        } catch (Exception e) {
            return -1;
        }
        if (manaVal < 0) {
            return -1;
        }
        return manaVal;
    }

    // MODIFIES: name, rarity, condition, manaCost, cardType (all the JTextFields)
    // EFFECTS: Clears the input from all JTextFields
    private void clearInput() {
        name.setText("");
        rarity.setText("");
        condition.setText("");
        manaCost.setText("");
        cardType.setText("");
    }

    // EFFECTS: helper function that returns a string with only the first letter capitalized
    private String capitalizeFirst(String s) {
        s = s.toLowerCase();
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }

    public void setAllCards(Deck allCards) {
        this.allCards = allCards;
    }
}
