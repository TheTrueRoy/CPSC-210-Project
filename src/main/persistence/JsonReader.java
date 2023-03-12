package persistence;

import model.Card;
import model.CardCollection;
import model.Deck;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Modelled after the JsonReader class from the provided JsonSerializationDemo
// Represents a reader that reads collection from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads collection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CardCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCardCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses card collection from JSON object and returns it
    private CardCollection parseCardCollection(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        CardCollection cc = new CardCollection(name);
        addCards(cc, jsonObject);
        addDecks(cc, jsonObject);
        return cc;
    }

    // MODIFIES: cc
    // EFFECTS: parses decks from JSON object and adds them to card collection
    private void addDecks(CardCollection cc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("decks");
        for (Object json : jsonArray) {
            JSONObject nextDeck = (JSONObject) json;
            addDeck(cc, nextDeck);
        }
    }

    // MODIFIES: cc
    // EFFECTS: parses deck from JSON object and adds it to card collection
    private void addDeck(CardCollection cc, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Deck deck = new Deck(name);
        cc.addDeck(deck);
        addCardsToDeck(deck, jsonObject);
    }

    // MODIFIES: cc
    // EFFECTS: parses cards from JSON object and adds them to deck
    private void addCardsToDeck(Deck deck, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cards");
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addCardToDeck(deck, nextCard);
        }
    }

    // MODIFIES: cc
    // EFFECTS: parses card from JSON object and adds it to deck
    private void addCardToDeck(Deck deck, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String rarity = jsonObject.getString("rarity");
        double condition = jsonObject.getDouble("condition");
        int manaCost = jsonObject.getInt("manacost");
        String cardType = jsonObject.getString("cardtype");
        Card card = new Card(name, rarity, condition, manaCost, cardType);
        deck.add(card);
    }

    // MODIFIES: cc
    // EFFECTS: parses cards from JSON object and adds them to card collection
    private void addCards(CardCollection cc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cards");
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addCard(cc, nextCard);
        }
    }

    // MODIFIES: cc
    // EFFECTS: parses card from JSON object and adds it to card collection
    private void addCard(CardCollection cc, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String rarity = jsonObject.getString("rarity");
        double condition = jsonObject.getDouble("condition");
        int manaCost = jsonObject.getInt("manacost");
        String cardType = jsonObject.getString("cardtype");
        Card card = new Card(name, rarity, condition, manaCost, cardType);
        cc.addCard(card);
    }
}
