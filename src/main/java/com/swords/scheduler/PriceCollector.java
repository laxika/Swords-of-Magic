package com.swords.scheduler;

import com.swords.model.Card;
import com.swords.model.CardCollection;
import com.swords.model.repository.CardCollectionRepository;
import com.swords.model.repository.CardRepository;
import com.swords.util.JSONUtils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class PriceCollector {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardCollectionRepository cardCollectionRepository;

    @Scheduled(fixedDelay = 21600000)
    public void process() {
        System.out.println("Downloading pricing data!");

        //TODO: refactor this!
        try {
            int counter = 0;
            boolean shouldRun = true;

            while (shouldRun) {
                JSONArray cards = JSONUtils.readJsonArrayFromUrl("https://api.deckbrew.com/mtg/cards?page=" + counter);

                if (cards.length() == 0) {
                    shouldRun = false;
                }

                for (int i = 0; i < cards.length(); i++) {
                    JSONObject card = cards.getJSONObject(i);

                    JSONArray editions = card.getJSONArray("editions");

                    for (int j = 0; j < editions.length(); j++) {
                        JSONObject edition = editions.getJSONObject(j);

                        if (edition.has("price")) {
                            Card actCard = cardRepository.findByMultiverseId(edition.getInt("multiverse_id"));
                            
                            if(actCard == null) {
                                continue;
                            }
                            
                            int price = edition.getJSONObject("price").getInt("median");

                            CardCollection cardCollection = cardCollectionRepository.findByIdOrCreateIfNotExists(actCard.getId());
                            cardCollection.setFoilPrice(price * 2);
                            cardCollection.setMintPrice(price);
                            cardCollection.setNearMintPrice(price);
                            cardCollection.setSlightlyPlayedPrice(price);
                            cardCollection.setModeratelyPlayedPrice(price);
                            cardCollection.setHeavilyPlayedPrice(price);

                            cardCollectionRepository.save(cardCollection);

                            System.out.println("Set new price: " + price + " for card: " + actCard.getName() + " [" + actCard.getExpansion() + "]");
                        }
                    }
                }

                counter++;
            }
        } catch (IOException | JSONException ex) {
            Logger.getLogger(PriceCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
