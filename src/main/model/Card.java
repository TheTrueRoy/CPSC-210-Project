package model;

import java.time.LocalDate;

// Represents a trading card, having a name, rarity, condition, mana cost, and type
public class Card {
    private String cardName;  // name of the card, multiple cards can have one name
    private String rarity;    // rarity of the card according to its labelling
    private double condition; // condition of the card according to PSA grading standards
    private int manaCost;     // mana cost in the upper right corner of the card
    private String cardType;  // type of card (instant, artifact, creature, land, enchantment, etc)

    /*
     * EFFECTS: name of card is set to ""; rarity is set to "Common";
     *          condition is set to -1/"Ungraded"; mana cost is set to 0;
     *          card type is set to token;
     */
    public Card() {
        this.cardName = "";
        this.rarity = "Common";
        this.condition = -1;
        this.manaCost = 0;
        this.cardType = "Token";
    }

    /*
     * REQUIRES: rarity is one of ["Common", "Uncommon", "Rare", "Mythic"]
     *           condition is either between 0-10 or is -1
     * EFFECTS: name of card is set to cardName; rarity is set to rarity;
     *          condition is set to condition; mana cost is set to manaCost;
     *          card type is set to cardType;
     */
    public Card(String cardName, String rarity, double condition, int manaCost, String cardType) {
        this.cardName = cardName;
        this.rarity = rarity;
        this.condition = condition;
        this.manaCost = manaCost;
        this.cardType = cardType;
    }

    /*
     * EFFECTS: returns numerical ranking of rarity with higher numbers being more rare.
     */
    private int rarityStringToInt(String rarity) {
        if (rarity.equals("Mythic")) {
            return 3;
        } else if (rarity.equals("Rare")) {
            return 2;
        } else if (rarity.equals("Uncommon")) {
            return 1;
        }
        return 0;
    }

    /*
     * EFFECTS: returns a string containing a summary of the card's fields
     */
    @Override
    public String toString() {
        String briefInfo = "A " + rarity + " " + cardName;
        return briefInfo + " (" + manaCost + " cost " + cardType + " card in " + getConditionAsString() + " condition)";
    }

    /*
     * EFFECTS: returns numerical representation of rarity
     */
    public int getRarityAsInt() {
        return rarityStringToInt(rarity);
    }

    public String getRarity() {
        return rarity;
    }

    public int getManaCost() {
        return manaCost;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCondition(double condition) {
        this.condition = condition;
    }

    /*
     * EFFECTS: returns string representation of condition based on standard PSA grading
     */
    public String getConditionAsString() {
        if (condition == 10) {
            return "Gem Mint";
        } else if (condition >= 9) {
            return "Mint";
        } else if (condition >= 7) {
            return "Near Mint";
        } else if (condition >= 5) {
            return "Excellent";
        } else if (condition >= 3) {
            return "Very Good";
        } else if (condition >= 2) {
            return "Good";
        } else if (condition >= 1) {
            return "Poor";
        } else if (condition >= 0) {
            return "Authentic Only";
        }
        return "Ungraded";
    }

    public String getCardName() {
        return cardName;
    }

    public double getCondition() {
        return condition;
    }
}
