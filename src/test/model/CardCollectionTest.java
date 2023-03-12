package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardCollectionTest {

    private CardCollection testCollection;

    @BeforeEach
    void runBefore() {
        testCollection = new CardCollection("Sandwich");
    }

    @Test
    void testSetName() {
        assertEquals("sandwich",testCollection.getName());
        testCollection.setName("house");
        assertEquals("house",testCollection.getName());
    }

    @Test
    void testSetDecks() {
        assertEquals(0,testCollection.getDecks().size());
        testCollection.addDeck(new Deck("Sponge"));
        assertEquals("Sponge",testCollection.getDecks().get(0).getDeckName());
    }

}
