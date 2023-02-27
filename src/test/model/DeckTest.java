package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    private Deck testDeck;

    @BeforeEach
    void runBefore() {
        testDeck = new Deck("Token");
    }

    @Test
    void testConstructor() {
        assertEquals("Token", testDeck.getDeckName());
        assertEquals(0, testDeck.getCards().size());
    }

    @Test
    void testAddRemoveCard() {
        assertEquals(0, testDeck.getCards().size());
        testDeck.remove(new Card());
        assertEquals(0, testDeck.getCards().size());
        Card c = new Card();
        testDeck.add(c);
        assertEquals(1, testDeck.getCards().size());
        assertEquals("", testDeck.getCards().get(0).getCardName());
        assertEquals(0, testDeck.getCards().get(0).getRarityAsInt());
        assertEquals(-1, testDeck.getCards().get(0).getCondition());
        testDeck.remove(c);
        assertEquals(0, testDeck.getCards().size());
    }

    @Test
    void testAddRemoveString() {
        assertEquals(0, testDeck.getCards().size());
        assertNull(testDeck.remove("sandwich"));
        assertEquals(0, testDeck.getCards().size());
        testDeck.add(new Card());
        assertEquals(1, testDeck.getCards().size());
        assertNull(testDeck.remove("sandwich"));
        assertEquals(1, testDeck.getCards().size());
        Card c = testDeck.remove("");
        assertEquals(c.getCardName(), "");
        assertEquals(0, testDeck.getCards().size());
    }

    @Test
    void testSort() {
        testDeck.add(new Card("bean","Common",5,2,"Food"));
        testDeck.add(new Card("smol bean","Uncommon",4,1,"Food"));
        testDeck.add(new Card("big bean","Mythic",4.5,7,"Food"));
        testDeck.add(new Card("bigger bean","Mythic",4.5,7,"Food"));
        assertEquals("bean", testDeck.getCards().get(0).getCardName());
        assertEquals("smol bean", testDeck.getCards().get(1).getCardName());
        assertEquals("big bean", testDeck.getCards().get(2).getCardName());
        assertEquals("bigger bean", testDeck.getCards().get(2).getCardName());
        testDeck.sort("name", "a");
        assertEquals("bean", testDeck.getCards().get(0).getCardName());
        assertEquals("big bean", testDeck.getCards().get(1).getCardName());
        assertEquals("bigger bean", testDeck.getCards().get(1).getCardName());
        assertEquals("smol bean", testDeck.getCards().get(2).getCardName());
        testDeck.sort("name", "d");
        assertEquals("smol bean", testDeck.getCards().get(0).getCardName());
        assertEquals("big bean", testDeck.getCards().get(1).getCardName());
        assertEquals("bigger bean", testDeck.getCards().get(1).getCardName());
        assertEquals("bean", testDeck.getCards().get(2).getCardName());
        testDeck.sort("rarity", "d");
        assertEquals("big bean", testDeck.getCards().get(0).getCardName());
        assertEquals("bigger bean", testDeck.getCards().get(0).getCardName());
        assertEquals("smol bean", testDeck.getCards().get(1).getCardName());
        assertEquals("bean", testDeck.getCards().get(2).getCardName());
        testDeck.sort("rarity", "a");
        assertEquals("bean", testDeck.getCards().get(0).getCardName());
        assertEquals("smol bean", testDeck.getCards().get(1).getCardName());
        assertEquals("big bean", testDeck.getCards().get(2).getCardName());
        assertEquals("bigger bean", testDeck.getCards().get(2).getCardName());
        testDeck.sort("condition", "d");
        assertEquals("bean", testDeck.getCards().get(0).getCardName());
        assertEquals("big bean", testDeck.getCards().get(1).getCardName());
        assertEquals("bigger bean", testDeck.getCards().get(1).getCardName());
        assertEquals("smol bean", testDeck.getCards().get(2).getCardName());
        testDeck.sort("condition", "a");
        assertEquals("smol bean", testDeck.getCards().get(0).getCardName());
        assertEquals("big bean", testDeck.getCards().get(1).getCardName());
        assertEquals("bigger bean", testDeck.getCards().get(1).getCardName());
        assertEquals("bean", testDeck.getCards().get(2).getCardName());
        testDeck.sort("token", "d");
        assertEquals("smol bean", testDeck.getCards().get(0).getCardName());
        assertEquals("big bean", testDeck.getCards().get(1).getCardName());
        assertEquals("bigger bean", testDeck.getCards().get(1).getCardName());
        assertEquals("bean", testDeck.getCards().get(2).getCardName());
        testDeck.sort("token", "a");
        assertEquals("smol bean", testDeck.getCards().get(0).getCardName());
        assertEquals("big bean", testDeck.getCards().get(1).getCardName());
        assertEquals("bigger bean", testDeck.getCards().get(1).getCardName());
        assertEquals("bean", testDeck.getCards().get(2).getCardName());
    }

    @Test
    void testListContents() {
        assertEquals("Sorry, this deck seems to be empty.\n", testDeck.listContents());
        testDeck.add(new Card("smol bean","Uncommon",4,1,"Food"));
        assertEquals("0) smol bean\n", testDeck.listContents());
        testDeck.add(new Card("big bean","Mythic",4.5,7,"Food"));
        assertEquals("0) smol bean\n1) big bean\n", testDeck.listContents());
    }

    @Test
    void testCardInfo() {
        assertEquals("Card not found", testDeck.cardInfo(0));
        Card smolBean = new Card("smol bean","Uncommon",4,1,"Food");
        testDeck.add(smolBean);
        assertEquals(smolBean.toString(), testDeck.cardInfo(0));
        assertEquals("Card not found", testDeck.cardInfo(1));
    }
}