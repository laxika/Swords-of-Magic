package com.swords.component;

import com.swords.model.Expansion;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ExpansionFactory {

    public Expansion createExpansionFromData(JSONObject expansionData) {
        Expansion expansion = new Expansion(expansionData.getString("code"));
        expansion.setName(expansionData.getString("name"));
        expansion.setReleaseDate(expansionData.getString("releaseDate"));
        expansion.setBorder(expansionData.getString("border"));
        expansion.setType(expansionData.getString("type"));

        if (expansionData.has("block")) {
            expansion.setBlock(expansionData.getString("block"));
        }

        if (expansionData.has("onlineOnly")) {
            expansion.setOnlineOnly(true);
        }

        if (expansionData.has("booster")) {
            //Todo
        }
        
        return expansion;
    }
}
