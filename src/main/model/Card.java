package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;

// Represents a trading card, having a name, rarity, condition, mana cost, and type
public class Card implements Writable {
    private final String cardName;  // name of the card, multiple cards can have one name
    private final String rarity;    // rarity of the card according to its labelling
    private double condition; // condition of the card according to PSA grading standards
    private final int manaCost;     // mana cost in the upper right corner of the card
    private final String cardType;  // type of card (instant, artifact, creature, land, enchantment, etc)

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
        EventLog.getInstance().logEvent(new Event("New generic card was created"));
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
        EventLog.getInstance().logEvent(new Event(cardName + " was created"));
    }

    /*
     * EFFECTS: returns numerical ranking of rarity with higher numbers being more rare.
     */
    private int rarityStringToInt(String rarity) {
        switch (rarity) {
            case "Mythic":
                return 3;
            case "Rare":
                return 2;
            case "Uncommon":
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", cardName);
        json.put("rarity", rarity);
        json.put("condition", condition);
        json.put("manacost", manaCost);
        json.put("cardtype", cardType);
        return json;
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
        EventLog.getInstance().logEvent(new Event(cardName + "'s condition was changed to " + condition));
        this.condition = condition;
    }

    public String getCardName() {
        return cardName;
    }

    public double getCondition() {
        return condition;
    }
}
