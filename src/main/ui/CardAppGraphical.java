package ui;

import model.Card;
import model.CardCollection;
import model.Deck;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Card Assistant Application (w/ a GUI)
public class CardAppGraphical extends JFrame {
    private static final String JSON_FOLDER = "./data/cardcollection.json";

    private CardCollection cc;
    private ArrayList<Deck> decks;
    private Deck allCards;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private CardCreationMenu cardCreationMenu;
    private DeckCreationMenu deckCreationMenu;
    private DeckDisplayMenu deckDisplayMenu;
    private CardDisplayMenu cardDisplayMenu;

    private CardDisplayMenu deckCardDisplayMenu;
    private CardDetailDisplayMenu cardDetailDisplayMenu;

    private Deck selectedDeck;

    // EFFECTS: starts the card application
    public CardAppGraphical() {
        super("Card Application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200,600));
        setVisible(true);
        setResizable(false);
        setLayout(new BorderLayout());
        init();
        JPanel interactionMenu = createInteractionMenu();
        add(interactionMenu);
        add(createSaveLoadMenu(), BorderLayout.SOUTH);
        pack();
    }

    // EFFECTS: Creates and returns a JPanel containing two JButton instances with the functionality
    //          of saving the current state of the program and loading the saved state of the program
    public JPanel createSaveLoadMenu() {
        JPanel saveLoadMenu = new JPanel();
        saveLoadMenu.setLayout(new GridLayout(2,1));
        JButton saveButton = new JButton("Save");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(e -> saveOption());
        JButton loadButton = new JButton("Load");
        loadButton.setActionCommand("load");
        loadButton.addActionListener(e -> loadOption());
        saveLoadMenu.add(saveButton);
        saveLoadMenu.add(loadButton);
        return saveLoadMenu;
    }

    // EFFECTS: Assembles are returns the structure of the user interface in a JPanel
    public JPanel createInteractionMenu() {
        JPanel interactionMenu = new JPanel();
        interactionMenu.setLayout(new GridLayout(2,2));
        deckDisplayMenu = new DeckDisplayMenu(decks, this);
        cardDisplayMenu = new CardDisplayMenu(allCards, this);
        deckCardDisplayMenu = new CardDisplayMenu(null, this);
        cardDetailDisplayMenu = new CardDetailDisplayMenu(null, this);
        deckCreationMenu = new DeckCreationMenu(this);
        cardCreationMenu = new CardCreationMenu(this);
        interactionMenu.add(deckCreationMenu);
        interactionMenu.add(deckDisplayMenu);
        interactionMenu.add(deckCardDisplayMenu);
        interactionMenu.add(cardCreationMenu);
        interactionMenu.add(cardDisplayMenu);
        interactionMenu.add(createControlPanel());
        return interactionMenu;
    }

    // EFFECTS: Creates and returns a JPanel containing the cardDetailDisplayMenu and both deck sorting buttons
    public JPanel createControlPanel() {
        JPanel holder = new JPanel();
        holder.setLayout(new GridLayout(2,1));
        holder.add(cardDetailDisplayMenu);
        JPanel buttonHolder = new JPanel();
        buttonHolder.add(createSortDownButton());
        buttonHolder.add(createSortUpButton());
        holder.add(buttonHolder);
        return holder;
    }

    // EFFECTS: Creates and returns a JButton instance with functionality of sorting the currently
    //          selected deck in descending order
    public JButton createSortDownButton() {
        JButton sortDown = new JButton("Z->A");
        sortDown.addActionListener(e -> {
            if (selectedDeck != null) {
                selectedDeck.sort("name","d");
                resetDeckCardDisplayMenu(selectedDeck);
            }
        });
        return sortDown;
    }

    // EFFECTS: Creates and returns a JButton instance with functionality of sorting the currently
    //          selected deck in ascending order
    public JButton createSortUpButton() {
        JButton sortUp = new JButton("A->Z");
        sortUp.addActionListener(e -> {
            if (selectedDeck != null) {
                selectedDeck.sort("name","u");
                resetDeckCardDisplayMenu(selectedDeck);
            }
        });
        return sortUp;
    }

    // MODIFIES: this
    // EFFECTS: initializes decks, the card collection, and the scanner
    private void init() {
        jsonWriter = new JsonWriter(JSON_FOLDER);
        jsonReader = new JsonReader(JSON_FOLDER);
        cc = new CardCollection("New");
        decks = cc.getDecks();
        allCards = cc.getAllCards();
    }

    // MODIFIES: this
    // EFFECTS: loads new card collection from json
    private void loadOption() {
        try {
            cc = jsonReader.read();
            decks = cc.getDecks();
            allCards = cc.getAllCards();
            System.out.println("Successfully loaded file from: " + JSON_FOLDER);
        } catch (IOException e) {
            decks = cc.getDecks();
            allCards = cc.getAllCards();
            System.out.println("Unable to read from file: " + JSON_FOLDER);
        }
        deckDisplayMenu.reset(decks);
        cardDisplayMenu.reset(allCards);
        deckCardDisplayMenu.reset(null);
        cardDetailDisplayMenu.reset(null);
        deckCreationMenu.setDecks(decks);
        cardCreationMenu.setAllCards(allCards);
        selectedDeck = null;
        this.revalidate();
        this.repaint();
    }

    // EFFECTS: saves the card collection to json
    private void saveOption() {
        try {
            jsonWriter.open();
            jsonWriter.write(cc);
            jsonWriter.close();
            System.out.println("Successfully saved file to: " + JSON_FOLDER);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_FOLDER);
        }
    }

    // MODIFIES: deckCardDisplayMenu, this
    // EFFECTS: Calls the reset function on the deckCardDisplayMenu with the provided deck and repaints the scene
    public void resetDeckCardDisplayMenu(Deck deck) {
        deckCardDisplayMenu.reset(deck);
        revalidate();
        repaint();
    }

    // MODIFIES: cardDisplayMenu, this
    // EFFECTS: Calls the reset function on the cardDisplayMenu with the provided deck and repaints the scene
    public void resetCardDisplayMenu(Deck deck) {
        cardDisplayMenu.reset(deck);
        revalidate();
        repaint();
    }

    // MODIFIES: cardDetailDisplayMenu, this
    // EFFECTS: Calls the reset function on the cardDetailDisplayMenu with the provided deck and repaints the scene
    public void resetCardDetailDisplayMenu(Card card) {
        cardDetailDisplayMenu.reset(card);
        revalidate();
        repaint();
    }

    public DeckDisplayMenu getDeckDisplayMenu() {
        return deckDisplayMenu;
    }

    public CardDisplayMenu getCardDisplayMenu() {
        return cardDisplayMenu;
    }

    public CardCollection getCardCollection() {
        return cc;
    }

    public Deck getSelectedDeck() {
        return selectedDeck;
    }

    public void setSelectedDeck(Deck selectedDeck) {
        this.selectedDeck = selectedDeck;
    }
}
