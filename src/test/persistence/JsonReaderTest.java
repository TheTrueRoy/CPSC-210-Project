package persistence;

import model.Deck;
import model.Card;
import model.CardCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Modelled after the JsonReaderTest class from the provided JsonSerializationDemo
// Tests methods found in the JsonReader class
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            CardCollection cc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCollection.json");
        try {
            CardCollection cc = reader.read();
            assertEquals("Empty Collection", cc.getName());
            assertEquals(0, cc.cardCount());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCollection() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCollection.json");
        try {
            CardCollection cc = reader.read();
            assertEquals("My Collection", cc.getName());
            List<Card> cards = cc.getAllCards().getCards();
            assertEquals(4, cards.size());
            checkCard("Bean", "Uncommon", 5.0, 6, "Food", cards.get(0));
            checkCard("Big bean", "Rare", 5.5, 8, "Food", cards.get(1));
            checkCard("King bean", "Mythic", 4.5, 10, "Creature", cards.get(2));
            checkCard("Hammer", "Common", 8.0, 6, "Weapon", cards.get(3));
            List<Deck> decks = cc.getDecks();
            Deck creationDeck = decks.get(0);
            assertEquals("Create", creationDeck.getDeckName());
            checkCard("Hammer", "Common", 8.0, 6, "Weapon", creationDeck.getCards().get(0));
            Deck beansDeck = decks.get(1);
            assertEquals("Beans", beansDeck.getDeckName());
            checkCard("Bean", "Uncommon", 5.0, 6, "Food", beansDeck.getCards().get(2));
            checkCard("Big bean", "Rare", 5.5, 8, "Food", beansDeck.getCards().get(0));
            checkCard("King bean", "Mythic", 4.5, 10, "Creature", beansDeck.getCards().get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}