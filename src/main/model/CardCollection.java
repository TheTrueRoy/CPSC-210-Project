package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

public class CardCollection implements Writable {
    private String name;
    private ArrayList<Deck> decks;
    private Deck allCards;

    /*
     * EFFECTS: name set to name, list of decks is set to an empty ArrayList of decks; allCards is set to an empty deck
     */
    public CardCollection(String name) {
        this.name = name;
        decks = new ArrayList<>();
        allCards = new Deck("all");
    }

    /*
     * EFFECTS: name set to name, list of decks is set to decks; list of all cards is set to a given deck allCards
     */
    public CardCollection(String name, ArrayList<Deck> decks, Deck allCards) {
        this.name = name;
        this.decks = decks;
        this.allCards = allCards;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("cards", cardsToJson());
        json.put("decks", decksToJson());
        return json;
    }

    // EFFECTS: returns cards in this card collection as a JSON array
    private JSONArray cardsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Card c : allCards.getCards()) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns decks in this card collection as a JSON array
    private JSONArray decksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Deck d : decks) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<Deck> decks) {
        this.decks = decks;
    }

    public Deck getAllCards() {
        return allCards;
    }

    public void setAllCards(Deck allCards) {
        this.allCards = allCards;
    }

    public void addCard(Card card) {
        allCards.add(card);
    }

    public void addDeck(Deck deck) {
        decks.add(deck);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int cardCount() {
        return allCards.getCards().size();
    }
}
