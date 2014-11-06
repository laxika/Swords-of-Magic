package com.swords.component;

import com.swords.model.Card;
import com.swords.util.JSONUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class CardBuilder {

    public Card buildCardFromJson(JSONObject cardData, String setCode) {
        Card card;

        if (cardData.has("number")) {
            card = new Card(setCode + "_" + cardData.getString("imageName") + "_" + cardData.getString("number"));
        } else {
            card = new Card(setCode + "_" + cardData.getString("imageName"));
        }

        card.setName(cardData.getString("name"));
        card.setExpansion(setCode);

        if (cardData.has("colors")) {
            card.setColor(JSONUtils.jsonArrayToStringArray(cardData.getJSONArray("colors")));
        }
        
        if (cardData.has("supertypes")) {
            card.setSupertypes(JSONUtils.jsonArrayToStringArray(cardData.getJSONArray("supertypes")));
        }
        
        if (cardData.has("types")) {
            card.setTypes(JSONUtils.jsonArrayToStringArray(cardData.getJSONArray("types")));
        }
        
        if (cardData.has("subtypes")) {
            card.setSubtypes(JSONUtils.jsonArrayToStringArray(cardData.getJSONArray("subtypes")));
        }

        if (cardData.has("manaCost")) {
            card.setManacost(cardData.getString("manaCost"));
        }

        if (cardData.has("rarity")) {
            card.setRarity(cardData.getString("rarity"));
        }

        if (cardData.has("text")) {
            card.setText(cardData.getString("text"));
        }

        if (cardData.has("flavor")) {
            card.setFlavor(cardData.getString("flavor"));
        }

        if (cardData.has("artist")) {
            card.setArtist(cardData.getString("artist"));
        }

        if (cardData.has("number")) {
            card.setNumber(cardData.getString("number"));
        }

        if (cardData.has("power")) {
            card.setPower(cardData.getString("power"));
        }

        if (cardData.has("toughness")) {
            card.setToughness(cardData.getString("toughness"));
        }

        if (cardData.has("layout")) {
            card.setLayout(cardData.getString("layout"));
        }

        if (cardData.has("imageName")) {
            card.setImageName(cardData.getString("imageName"));
        }

        if (cardData.has("multiverseid")) {
            card.setMultiverseId(cardData.getInt("multiverseid"));
        }

        return card;
    }
}
