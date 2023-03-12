package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Comparator;

// Represents a deck with a name and an arbitrary number of cards within
public class Deck implements Writable {
    private final String deckName;       // name of the deck
    private final ArrayList<Card> cards; // list of cards within

    /*
     * EFFECTS: the name of the deck is set to deckName;
     *          the list of cards is set to an empty ArrayList
     */
    public Deck(String deckName) {
        this.deckName = deckName;
        this.cards = new ArrayList<>();
    }

    public String getDeckName() {
        return deckName;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds card to deck
     */
    public void add(Card c) {
        cards.add(c);
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes card from deck
     */
    public void remove(Card c) {
        cards.remove(c);
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes first card from deck with same name as given string and the returns the card that was removed
     */
    public Card remove(String s) {
        for (Card c : cards) {
            if (c.getCardName().equals(s)) {
                cards.remove(c);
                return c;
            }
        }
        return null;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sorts cards in this deck according to a direction and a comparator;
     *          a comparator of name will sort alphabetically; rarity sorts by rarity; condition sorts by condition
     *          a direction of a will sort in ascending order; d will sort in descending order
     */
    public void sort(String comparator, String direction) {
        cards.sort((o1, o2) -> {
            if (direction.equals("d")) {
                Card temp = o1;
                o1 = o2;
                o2 = temp;
            }
            switch (comparator) {
                case "name":
                    return o1.getCardName().compareToIgnoreCase(o2.getCardName());
                case "rarity": {
                    double c1 = o1.getRarityAsInt();
                    double c2 = o2.getRarityAsInt();
                    return Double.compare(c1, c2);
                }
                case "condition": {
                    double c1 = o1.getCondition();
                    double c2 = o2.getCondition();
                    return Double.compare(c1, c2);
                }
            }
            return 0;
        });
    }

    /*
     * EFFECTS: returns a string representation of the cards in the list containing their order and names
     */
    public String listContents() {
        StringBuilder output = new StringBuilder();
        int index = 0;
        for (Card c : getCards()) {
            output.append(index).append(") ").append(c.getCardName()).append("\n");
            index++;
        }
        if (index == 0) {
            return "Sorry, this deck seems to be empty.\n";
        }
        return output.toString();
    }

    /*
     * REQUIRES: index <= deck.getCards().size()
     * EFFECTS: returns the info for the card at a given index in the deck
     */
    public String cardInfo(int index) {
        Card c;
        try {
            c = getCards().get(index);
        } catch (Exception e) {
            return "Card not found";
        }
        return c.toString();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", deckName);
        json.put("cards", cardsToJson());
        return json;
    }

    // EFFECTS: returns cards in this deck as a JSON array
    private JSONArray cardsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Card c : cards) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
