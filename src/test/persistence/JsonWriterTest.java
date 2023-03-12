package persistence;

import model.Card;
import model.Deck;
import model.CardCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            CardCollection cc = new CardCollection("My Collection");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCollection() {
        try {
            CardCollection cc = new CardCollection("My Collection");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCollection.json");
            writer.open();
            writer.write(cc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCollection.json");
            cc = reader.read();
            assertEquals("My Collection", cc.getName());
            assertEquals(0, cc.cardCount());
            assertEquals(0, cc.getDecks().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCollection() {
        try {
            CardCollection cc = new CardCollection("My Collection");
            cc.addCard(new Card("Bean", "Uncommon", 5.0, 6, "Food"));
            cc.addCard(new Card("Big Bean", "Rare", 5.5, 8, "Food"));
            cc.addCard(new Card("Hammer", "Common", 8.0, 6, "Weapon"));
            Deck emptyDeck = new Deck("Empty");
            cc.addDeck(emptyDeck);
            Deck beanDeck = new Deck("Beans");
            beanDeck.add(new Card("Bean", "Uncommon", 5.0, 6, "Food"));
            beanDeck.add(new Card("Big Bean", "Rare", 5.5, 8, "Food"));
            cc.addDeck(beanDeck);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCollection.json");
            writer.open();
            writer.write(cc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCollection.json");
            cc = reader.read();
            assertEquals("My Collection", cc.getName());
            List<Card> cards = cc.getAllCards().getCards();
            assertEquals(3, cards.size());
            checkCard("Bean", "Uncommon", 5.0, 6, "Food", cards.get(0));
            checkCard("Big Bean", "Rare", 5.5, 8, "Food", cards.get(1));
            checkCard("Hammer", "Common", 8.0, 6, "Weapon", cards.get(2));
            List<Deck> decks = cc.getDecks();
            emptyDeck = decks.get(0);
            assertEquals("Empty", emptyDeck.getDeckName());
            assertEquals(0, emptyDeck.getCards().size());
            beanDeck = decks.get(1);
            assertEquals("Beans", beanDeck.getDeckName());
            checkCard("Bean", "Uncommon", 5.0, 6, "Food", beanDeck.getCards().get(0));
            checkCard("Big Bean", "Rare", 5.5, 8, "Food", beanDeck.getCards().get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}