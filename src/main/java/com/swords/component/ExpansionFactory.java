package com.swords.component;

import com.swords.model.Expansion;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ExpansionFactory {

    private final SimpleDateFormat releaseDateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public Expansion createExpansionFromData(JSONObject expansionData) throws ParseException {
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

        return expansion;
    }
}
