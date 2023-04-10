package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

public class CardCollection implements Writable {
    private String name;
    private ArrayList<Deck> decks;
    private final Deck allCards;

    /*
     * EFFECTS: name set to name, list of decks is set to an empty ArrayList of decks; allCards is set to an empty deck
     */
    public CardCollection(String name) {
        this.name = name;
        decks = new ArrayList<>();
        allCards = new Deck("all");
        EventLog.getInstance().logEvent(new Event("The card collection was initialized"));
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
        EventLog.getInstance().logEvent(new Event("The card collection's decks were changed to a new list of decks"));
    }

    public Deck getAllCards() {
        return allCards;
    }

    public void addCard(Card card) {
        allCards.add(card);
        EventLog.getInstance().logEvent(new Event(card.getCardName() + " was added to the collection."));
    }

    public void addDeck(Deck deck) {
        EventLog.getInstance().logEvent(new Event(deck.getDeckName() + " was added to the collection."));
        decks.add(deck);
    }

    public void setName(String name) {
        EventLog.getInstance().logEvent(new Event("The card collection's named was changed to " + name));
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int cardCount() {
        return allCards.getCards().size();
    }
}
