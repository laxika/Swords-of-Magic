package com.swords.component;

import com.swords.model.Card;
import com.swords.model.Ruling;
import com.swords.util.JSONUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class CardBuilder {

    public Card buildCardFromJson(JSONObject cardData, String setCode) {
        Card card = new Card(this.buildCardId(cardData, setCode));

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
            card.setCmc(cardData.getDouble("cmc"));
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

        if (cardData.has("rulings")) {
            JSONArray rulings = cardData.getJSONArray("rulings");
            Ruling[] finalRulings = new Ruling[rulings.length()];
            
            for(int i = 0; i < rulings.length(); i++) {
                JSONObject ruling = rulings.getJSONObject(i);
                
                finalRulings[i] = new Ruling(ruling.getString("date"), ruling.getString("text"));
            }
            
            card.setRulings(finalRulings);
        }

        return card;
    }

    private String buildCardId(JSONObject cardData, String setCode) {
        return cardData.has("number")
                ? setCode + "_" + cardData.getString("imageName") + "_" + cardData.getString("number").replace(" ", "_")
                : setCode + "_" + cardData.getString("imageName").replace(" ", "_");
    }
}
