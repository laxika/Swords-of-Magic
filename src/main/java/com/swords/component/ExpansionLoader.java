package com.swords.component;

import com.swords.controller.AdminController;
import com.swords.model.Card;
import com.swords.model.Expansion;
import com.swords.model.repository.CardRepository;
import com.swords.model.repository.ExpansionRepository;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.logging.Level;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ExpansionLoader {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private ExpansionRepository expansionRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardBuilder cardFactory;
    @Autowired
    private ExpansionBuilder expansionFactory;

    private final Resource carddata;
    private final Resource seticondata;

    @Autowired
    public ExpansionLoader(ApplicationContext appContext) {
        carddata = appContext.getResource("classpath:/data/carddata.json");
        seticondata = appContext.getResource("classpath:/data/setsymbols.json");
    }

    public void reloadExpansionData() {
        logger.info("Started loading the cards!");

        try {
            JSONObject carddataJson = this.loadJsonData();
            JSONObject seticondataJson = this.loadSetIconJsonData();
            Iterator<?> keys = carddataJson.keys();

            while (keys.hasNext()) {
                this.loadExpansion((JSONObject) carddataJson.get((String) keys.next()), seticondataJson);
            }
        } catch (IOException | JSONException e) {
            logger.error(e.getMessage());
        }
    }

    private JSONObject loadJsonData() throws MalformedURLException, IOException {
        return new JSONObject(new JSONTokener(new InputStreamReader(carddata.getInputStream())));
    }

    private JSONObject loadSetIconJsonData() throws MalformedURLException, IOException {
        return new JSONObject(new JSONTokener(new InputStreamReader(seticondata.getInputStream())));
    }

    private void loadExpansion(JSONObject expansionData, JSONObject seticonData) {
        try {
            Expansion expansion = expansionFactory.buildExpansionFromJson(expansionData, seticonData);
            
            this.saveExpansion(expansion);
            this.loadCardsInExpansion(expansion, expansionData.getJSONArray("cards"));
            
            logger.info("Loaded expansion: " + expansion.getName());
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(ExpansionLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveExpansion(Expansion expansion) {
        expansionRepository.insert(expansion);
    }

    private void loadCardsInExpansion(Expansion expansion, JSONArray carddata) {
        for (int i = 0; i < carddata.length(); i++) {
            Card card = cardFactory.buildCardFromJson(carddata.getJSONObject(i), expansion.getCode());

            this.saveCard(card);
        }
    }

    private void saveCard(Card card) {
        cardRepository.insert(card);
    }

}
