package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Tests methods found in the CardCollection class
public class CardCollectionTest {

    private CardCollection testCollection;

    @BeforeEach
    void runBefore() {
        testCollection = new CardCollection("sandwich");
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
        ArrayList<Deck> decks = new ArrayList<>();
        decks.add(new Deck("Sponge"));
        testCollection.setDecks(decks);
        assertEquals("Sponge",testCollection.getDecks().get(0).getDeckName());
    }

}
