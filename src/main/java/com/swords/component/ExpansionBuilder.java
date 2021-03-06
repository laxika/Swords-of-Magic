package com.swords.component;

import com.swords.model.Expansion;
import com.swords.util.JSONUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ExpansionBuilder {

    private final SimpleDateFormat releaseDateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public Expansion buildExpansionFromJson(JSONObject expansionData, JSONObject seticonData) throws ParseException {
        Date releaseDate = releaseDateFormatter.parse(expansionData.getString("releaseDate"));

        Expansion expansion = new Expansion(expansionData.getString("code"));
        expansion.setName(expansionData.getString("name"));
        expansion.setReleaseDate(releaseDate);
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
        
        if(seticonData.has(expansion.getId())) {
            expansion.setSeticons(JSONUtils.jsonArrayToStringArray(seticonData.getJSONArray(expansion.getId())));
        }

        return expansion;
    }
}
