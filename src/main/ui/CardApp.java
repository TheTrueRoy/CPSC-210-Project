package ui;

import model.Card;
import model.CardCollection;
import model.Deck;
import persistence.JsonReader;
import persistence.JsonWriter;

// Card Assistant Application
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CardApp {
    private static final String JSON_FOLDER = "./data/cardcollection.json";

    private CardCollection cc;
    private ArrayList<Deck> decks;
    private Deck allCards;
    private Scanner scan;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: starts the card application
    public CardApp() {
        init();
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: initializes decks, the card collection, and the scanner
    private void init() {
        scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_FOLDER);
        jsonReader = new JsonReader(JSON_FOLDER);
        cc = new CardCollection("New");
        System.out.print("Load previous save? (y/n): ");
        if (scan.next().equals("y")) {
            loadOption();
        } else {
            decks = cc.getDecks();
            allCards = cc.getAllCards();
        }
        runApp();
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
    }

    // EFFECTS: saves the card collection to json
    private void saveOption() {
        try {
            System.out.print("Name Save File: ");
            cc.setName(scan.next());
            jsonWriter.open();
            jsonWriter.write(cc);
            jsonWriter.close();
            System.out.println("Successfully saved file to: " + JSON_FOLDER);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_FOLDER);
        }
    }

    // MODIFIES: this
    // EFFECTS: ticks the UI
    private void runApp() {
        boolean quit = false;

        showInstructions();

        while (!quit) {
            quit = interpretInput();
        }
    }

    // EFFECTS: prints instructions for the user into the console
    private void showInstructions() {
        System.out.println("Type \"deck\" for deck options");
        System.out.println("Type \"card\" for card options");
        System.out.println("Type \"load\" to load saved collection");
        System.out.println("Type \"save\" to save current collection");
        System.out.println("Type \"quit\" to exit");
    }

    // MODIFIES: this
    // EFFECTS:  directs the user to the next menu or quits the program based on their input
    private boolean interpretInput() {
        System.out.print("> ");
        String input = scan.next().toLowerCase();
        if (input.equals("quit")) {
            return true;
        } else if (input.equals("card")) {
            displayCardOptions();
            processCardOptions();
        } else if (input.equals("deck")) {
            displayDeckOptions();
            processDeckOptions();
        } else if (input.equals("load")) {
            loadOption();
        } else if (input.equals("save")) {
            saveOption();
        } else {
            System.out.println("Command not understood, please check your spelling and try again.");
        }
        return false;
    }

    // EFFECTS: displays a list of card-related options for the user
    private void displayCardOptions() {
        System.out.println("Type \"add\" to add a new card to your collection");
        System.out.println("Type \"remove\" to remove an old card from your collection");
        System.out.println("Type \"list\" to list all cards in your collection");
        System.out.println("Type \"info\" to show relevant information on a specific card");
        System.out.println("Type anything else to go back");
    }

    // MODIFIES: this
    // EFFECTS: directs the user to the next series of inputs related to their intent
    private void processCardOptions() {
        System.out.print("> ");
        String input = scan.next().toLowerCase();
        switch (input) {
            case "add":
                System.out.println(addCard());
                break;
            case "remove":
                System.out.println(removeCard());
                break;
            case "list":
                System.out.print(listCards());
                break;
            case "info":
                System.out.println(cardInfo(allCards));
                break;
            default:
                System.out.println("You have been returned to the main menu.");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: provides a series of inputs to the user in order to create a new card and then returns a success message
    private String addCard() {
        System.out.print("Enter Card Name: ");
        String name = capitalizeFirst(scan.next());
        System.out.print("Enter Rarity (Common, Uncommon, Rare, Mythic): ");
        String rarity = pullRarity();
        System.out.print("Enter Condition (0-10 for a graded card, -1 is ungraded): ");
        double condition = pullCondition();
        System.out.print("Enter Mana Cost: ");
        int mana = pullMana();
        System.out.print("Enter Card Type: ");
        String type = capitalizeFirst(scan.next());
        Card newCard = new Card(name, rarity, condition, mana, type);
        allCards.add(newCard);
        return "Successfully added " + name + " to your collection.";
    }

    // MODIFIES: this
    // EFFECTS: returns rarity if the user inputs a valid value, or returns the value of a repeated query if invalid
    private String pullRarity() {
        String rarity = capitalizeFirst(scan.next());
        if (rarity.equals("Common") || rarity.equals("Uncommon") || rarity.equals("Rare") || rarity.equals("Mythic")) {
            return rarity;
        }
        System.out.println("Rarity undefined, try again.");
        return pullRarity();
    }

    // MODIFIES: this
    // EFFECTS: returns condition if the user inputs a valid value, or returns the value of a repeated query if invalid
    private double pullCondition() {
        double condition;
        try {
            condition = Double.parseDouble(scan.next());
        } catch (Exception e) {
            condition = -2;
        }
        if ((condition <= 10 && condition >= 0) || condition == -1) {
            return condition;
        }
        System.out.println("Condition undefined, try again.");
        return pullCondition();
    }

    // MODIFIES: this
    // EFFECTS: returns mana cost if the user inputs a valid value, or returns the value of a repeated query if invalid
    private int pullMana() {
        int mana;
        try {
            mana = Integer.parseInt(scan.next());
        } catch (Exception e) {
            System.out.println("Mana cost undefined, try again.");
            return pullMana();
        }
        return mana;
    }

    // MODIFIES: this
    // EFFECTS: removes card from collection and all decks that it is contained in if possible, then returns a
    //          success/failure message
    private String removeCard() {
        System.out.print("Enter Card Name: ");
        String name = capitalizeFirst(scan.next());
        Card c = allCards.remove(name);
        if (c != null) {
            for (Deck d : decks) {
                d.remove(c);
            }
            return "First instance of card successfully removed";
        }
        return "Card not found in collection";
    }

    // EFFECTS: returns a string representation of all cards in collection there is >= 1, else returns a failure message
    private String listCards() {
        StringBuilder output = new StringBuilder();
        int index = 0;
        for (Card c : allCards.getCards()) {
            output.append(index).append(") ").append(c.getCardName()).append("\n");
            index++;
        }
        if (index == 0) {
            return "Sorry, you don't seem to have any cards.\n";
        }
        return output.toString();
    }

    // EFFECTS: returns information about a card in a given deck, or a failure message if no such index exists
    private String cardInfo(Deck d) {
        System.out.print("Enter Card Index: ");
        int index;
        try {
            index = Integer.parseInt(scan.next());
        } catch (Exception e) {
            return "Invalid Index";
        }
        if (d == null) {
            return "Invalid Deck";
        }
        System.out.println(d.cardInfo(index));
        System.out.print("Would you like to update this cards condition? y/n: ");
        String response = scan.next();
        if (response.equals("y")) {
            System.out.print("Enter new condition: ");
            d.getCards().get(index).setCondition(pullCondition());
        }
        return "You are now back at the main menu.";
    }

    // EFFECTS: returns a string with only the first letter capitalized
    private String capitalizeFirst(String s) {
        s = s.toLowerCase();
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }

    // EFFECTS: displays options for the user in the deck menu
    private void displayDeckOptions() {
        System.out.println("Type \"create\" to create a new deck");
        System.out.println("Type \"delete\" to delete an old deck");
        System.out.println("Type \"list\" to list all decks");
        System.out.println("Type \"sort\" to sort a deck");
        System.out.println("Type \"add\" to add cards to a deck");
        System.out.println("Type \"remove\" to remove cards from a deck");
        System.out.println("Type \"show\" to show all cards from a deck");
        System.out.println("Type \"info\" for info on a specific card from a deck");
        System.out.println("Type anything else to go back");
    }

    // MODIFIES: this
    // EFFECTS: processes user input for the deck menu
    private void processDeckOptions() {
        String input = scan.next().toLowerCase();
        if (input.equals("list")) {
            System.out.print(listDecks());
            return;
        }
        System.out.print("Enter Deck Name: ");
        String name = capitalizeFirst(scan.next());
        Deck deck = getDeckFromName(name);
        if (input.equals("create")) {
            System.out.println(createDeck(name));
        } else if (input.equals("delete")) {
            System.out.println(deleteDeck(deck));
        } else if (input.equals("sort")) {
            System.out.print(sortDeck(deck));
        } else if (input.equals("add")) {
            System.out.println(addToDeck(deck));
        } else if (input.equals("remove")) {
            System.out.println(removeFromDeck(deck));
        } else if (input.equals("show")) {
            System.out.print(showDeck(deck));
        } else if (input.equals("info")) {
            System.out.println(cardInfo(deck));
        }
    }

    // EFFECTS: returns the first Deck from decks that shares its name with a given string, else null if none are found
    private Deck getDeckFromName(String name) {
        for (Deck d : decks) {
            if (d.getDeckName().equals(name)) {
                return d;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: adds a new empty deck with given name into the list of decks, then returns a success message
    private String createDeck(String name) {
        decks.add(new Deck(name));
        return "Successfully added " + name + " to your collection.";
    }

    // MODIFIES: this
    // EFFECTS: removes a deck with given name form the list of decks if possible,
    //          then returns a success/failure message based on the whether it was
    private String deleteDeck(Deck d) {
        if (d != null) {
            decks.remove(d);
            return "First instance of deck successfully removed";
        }
        return "Deck not found in collection";
    }

    // EFFECTS: produces a string representation of all decks currently listed in decks, or a failure message if none
    private String listDecks() {
        StringBuilder output = new StringBuilder();
        int index = 0;
        for (Deck d : decks) {
            output.append(index).append(") ").append(d.getDeckName()).append("\n");
            index++;
        }
        if (index == 0) {
            return "Sorry, you don't seem to have any decks.\n";
        }
        return output.toString();
    }

    // MODIFIES: this
    // EFFECTS: sorts a given deck the produces a success message, or a failure message if no deck was null
    private String sortDeck(Deck deck) {
        if (deck != null) {
            System.out.print("Select Comparator (name, rarity, condition): ");
            String comparator = scan.next().toLowerCase();
            System.out.print("Select Order (d for descending, a for ascending): ");
            String direction = scan.next().toLowerCase();
            deck.sort(comparator, direction);
            return "Deck was successfully sorted\n" + deck.listContents();
        }
        return "Deck could not be located\n";
    }

    // MODIFIES: this
    // EFFECTS: adds a card with a given to a deck and returns a success message, unless all cards with that name
    //          are already contained within, in which case it returns a failure message
    private String addToDeck(Deck deck) {
        System.out.print("Enter Card Name: ");
        String card = capitalizeFirst(scan.next());
        for (Card c : allCards.getCards()) {
            if (c.getCardName().equals(card)) {
                if (!deck.getCards().contains(c)) {
                    deck.getCards().add(c);
                    return card + " successfully added.";
                }
            }
        }
        return card + " could not be added.";
    }

    // MODIFIES: this
    // EFFECTS: removes the first card with a given name from a given deck if possible, then returns a
    //          success/failure message
    private String removeFromDeck(Deck deck) {
        System.out.print("Enter Card Name: ");
        String card = capitalizeFirst(scan.next());
        if (deck.remove(card) != null) {
            return card + " successfully removed.";
        }
        return card + " could not be found.";
    }

    // EFFECTS: returns a string representation of all the cards in a given deck
    private String showDeck(Deck deck) {
        if (deck != null) {
            return deck.listContents();
        }
        return "Deck could not be found.\n";
    }
}
