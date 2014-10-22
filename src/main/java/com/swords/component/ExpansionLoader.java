package com.swords.component;

import com.swords.controller.AdminController;
import com.swords.model.Card;
import com.swords.model.Expansion;
import com.swords.model.repository.CardRepository;
import com.swords.model.repository.ExpansionRepository;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpansionLoader {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private static final String CARD_DATA_URL = "http://mtgjson.com/json/AllSets-x.json";

    @Autowired
    private ExpansionRepository expansionRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardFactory cardFactory;
    @Autowired
    private ExpansionFactory expansionFactory;

    public void reloadExpansionData() {
        logger.info("Started loading the cards!");

        try {
            JSONObject json = this.loadJsonData();
            Iterator<?> keys = json.keys();

            while (keys.hasNext()) {
                this.loadExpansion((JSONObject) json.get((String) keys.next()));
            }
        } catch (IOException | JSONException e) {
            logger.error(e.getMessage());
        }
    }

    private JSONObject loadJsonData() throws MalformedURLException, IOException {
        URL setdata = new URL(CARD_DATA_URL);
        Reader in = new InputStreamReader(setdata.openStream());

        return new JSONObject(new JSONTokener(in));
    }

    private void loadExpansion(JSONObject expansionData) {
        Expansion expansion = expansionFactory.createExpansionFromData(expansionData);

        this.saveExpansion(expansion);
        this.loadCardsInExpansion(expansion, expansionData.getJSONArray("cards"));
        
        logger.info("Loaded expansion: " + expansion.getName());
    }
    
    private void saveExpansion(Expansion expansion) {
        expansionRepository.insert(expansion);
    }
    
    private void loadCardsInExpansion(Expansion expansion, JSONArray carddata) {
        for (int i = 0; i < carddata.length(); i++) {
            Card card = cardFactory.createCardFromData(carddata.getJSONObject(i), expansion.getCode());

            this.saveCard(card);
        }
    }
    
    private void saveCard(Card card) {
        cardRepository.insert(card);
    }

}
