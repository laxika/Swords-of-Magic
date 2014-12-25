package com.swords.scheduler;

import com.swords.model.Card;
import com.swords.model.CardCollection;
import com.swords.model.repository.CardCollectionRepository;
import com.swords.model.repository.CardRepository;
import com.swords.util.JSONUtils;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class PriceCollector {

    private static final Logger logger = LoggerFactory.getLogger(PriceCollector.class);

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardCollectionRepository cardCollectionRepository;

    @Scheduled(fixedDelay = 21600000)
    public void process() {
        logger.info("Downloading pricing data!");

        try {
            int counter = 0;
            JSONArray cards;

            do {
                cards = JSONUtils.readJsonArrayFromUrl("https://api.deckbrew.com/mtg/cards?page=" + counter);

                this.processCards(cards);

                counter++;
            } while (cards.length() != 0);
        } catch (IOException | JSONException ex) {
            logger.error("Failed to parse pricing data!", ex);
        }
    }

    private void processCards(JSONArray cards) {
        for (int i = 0; i < cards.length(); i++) {
            JSONObject card = cards.getJSONObject(i);

            this.processEditions(card.getJSONArray("editions"));
        }
    }

    private void processEditions(JSONArray editions) {
        for (int j = 0; j < editions.length(); j++) {
            JSONObject edition = editions.getJSONObject(j);

            if (edition.has("price")) {
                Card actCard = cardRepository.findByMultiverseId(edition.getInt("multiverse_id"));

                if(actCard == null) {
                    continue;
                }

                CardCollection cardCollection = this.parseCardPricing(edition, actCard);

                cardCollectionRepository.save(cardCollection);

                logger.info("Set new price: " + cardCollection.getMintPrice() + " for card: " + actCard.getName() + " [" + actCard.getExpansion() + "]");
            }
        }
    }

    private CardCollection parseCardPricing(JSONObject edition, Card actCard) {
        int price = edition.getJSONObject("price").getInt("median");

        CardCollection cardCollection = cardCollectionRepository.findByIdOrCreateIfNotExists(actCard.getId());
        cardCollection.setFoilPrice(price * 2);
        cardCollection.setMintPrice(price);
        cardCollection.setNearMintPrice(price);
        cardCollection.setSlightlyPlayedPrice(price);
        cardCollection.setModeratelyPlayedPrice(price);
        cardCollection.setHeavilyPlayedPrice(price);

        return cardCollection;
    }
}
