package com.swords.component;

import com.swords.model.Card;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class CardFactory {

    public Card createCardFromData(JSONObject cardData, String setCode) {
        Card card;

        if (cardData.has("number")) {
            card = new Card(setCode + "_" + cardData.getString("imageName") + "_" + cardData.getString("number"));
        } else {
            card = new Card(setCode + "_" + cardData.getString("imageName"));
        }

        card.setName(cardData.getString("name"));

        if (cardData.has("colors")) {
            JSONArray colors = cardData.getJSONArray("colors");

            String[] finalColors = new String[colors.length()];
            for (int colorId = 0; colorId < colors.length(); colorId++) {
                finalColors[colorId] = colors.getString(colorId);
            }

            card.setColor(finalColors);
        }

        if (cardData.has("manaCost")) {
            card.setManacost(cardData.getString("manaCost"));
        }

        return card;
    }
}
