package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {
    private Card testCard;
    private Card testFilledCard;

    @BeforeEach
    void runBefore() {
        testCard = new Card();
        testFilledCard = new Card("bean", "Uncommon", 10, 2, "Food");
    }

    @Test
    void testConstructor() {
        assertEquals("", testCard.getCardName());
        assertEquals("Common", testCard.getRarity());
        assertEquals(-1, testCard.getCondition());
        assertEquals(0, testCard.getManaCost());
        assertEquals("Token", testCard.getCardType());

        assertEquals("bean", testFilledCard.getCardName());
        assertEquals("Uncommon", testFilledCard.getRarity());
        assertEquals(10, testFilledCard.getCondition());
        assertEquals(2, testFilledCard.getManaCost());
        assertEquals("Food", testFilledCard.getCardType());
    }

    @Test
    void testToString() {
        assertEquals("A Common  (0 cost Token card in Ungraded condition)", testCard.toString());
        assertEquals("A Uncommon bean (2 cost Food card in Gem Mint condition)", testFilledCard.toString());
    }



    @Test
    void testGetRarityAsInt() {
        assertEquals(testCard.getRarityAsInt(), 0);
        assertEquals(testFilledCard.getRarityAsInt(), 1);
    }

}