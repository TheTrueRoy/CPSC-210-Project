package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Tests methods found in the Card class
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
    void testGetConditionAsString() {
        assertEquals(testFilledCard.getConditionAsString(), "Gem Mint");
        testFilledCard = new Card("bean", "Rare", 9, 2, "Food");
        assertEquals(testFilledCard.getConditionAsString(), "Mint");
        testFilledCard = new Card("bean", "Rare", 7, 2, "Food");
        assertEquals(testFilledCard.getConditionAsString(), "Near Mint");
        testFilledCard = new Card("bean", "Rare", 5, 2, "Food");
        assertEquals(testFilledCard.getConditionAsString(), "Excellent");
        testFilledCard = new Card("bean", "Rare", 3, 2, "Food");
        assertEquals(testFilledCard.getConditionAsString(), "Very Good");
        testFilledCard = new Card("bean", "Rare", 2, 2, "Food");
        assertEquals(testFilledCard.getConditionAsString(), "Good");
        testFilledCard = new Card("bean", "Rare", 1, 2, "Food");
        assertEquals(testFilledCard.getConditionAsString(), "Poor");
        testFilledCard = new Card("bean", "Rare", 0, 2, "Food");
        assertEquals(testFilledCard.getConditionAsString(), "Authentic Only");
        testFilledCard = new Card("bean", "Rare", -1, 2, "Food");
        assertEquals(testFilledCard.getConditionAsString(), "Ungraded");
    }

    @Test
    void testSetCondition() {
        assertEquals(10, testFilledCard.getCondition());
        testFilledCard.setCondition(8);
        assertEquals(8, testFilledCard.getCondition());
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
        testFilledCard = new Card("bean", "Rare", 10, 2, "Food");
        assertEquals(testFilledCard.getRarityAsInt(), 2);
    }

}