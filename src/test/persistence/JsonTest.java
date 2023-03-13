package persistence;

import model.Card;
import model.Deck;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Modelled after the JsonTest class from the provided JsonSerializationDemo
// Provides useful methods for other JSON testing classes
public class JsonTest {
    protected void checkCard(String name, String rarity, double condi, int mana, String cardType, Card card) {
        assertEquals(name, card.getCardName());
        assertEquals(rarity, card.getRarity());
        assertEquals(condi, card.getCondition());
        assertEquals(mana, card.getManaCost());
        assertEquals(cardType, card.getCardType());
    }
}
