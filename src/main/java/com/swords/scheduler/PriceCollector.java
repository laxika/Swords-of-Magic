package com.swords.scheduler;

import com.sun.deploy.net.URLEncoder;
import com.swords.model.Card;
import com.swords.model.CardCollection;
import com.swords.model.Expansion;
import com.swords.model.repository.CardCollectionRepository;
import com.swords.model.repository.CardRepository;
import com.swords.model.repository.ExpansionRepository;
import com.swords.util.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@EnableScheduling
public class PriceCollector {

    private static final Logger logger = LoggerFactory.getLogger(PriceCollector.class);

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardCollectionRepository cardCollectionRepository;
    @Autowired
    private ExpansionRepository expansionRepository;

    @Scheduled(fixedDelay = 21600000)
    public void process() {
        logger.info("Downloading pricing data!");

        List<Expansion> expansionList = expansionRepository.findAll();

        for (Expansion expansion : expansionList) {
            try {
                JSONArray cards = JSONUtils.readJsonArrayFromUrl("http://notional-buffer-750.appspot.com/api/tcgplayer/setPrices.json?cardset=" + URLEncoder.encode(this.mapExpansionName(expansion.getName()), "UTF-8"));

                logger.info("Parsing price data for set: "+expansion.getName());

                this.processCards(cards, expansion.getId());
            } catch (IOException | JSONException ex) {
                logger.error("Failed to parse pricing data for set: " + expansion.getName(), ex);
            }
        }
    }

    private void processCards(JSONArray cards, String expansionId) {
        for (int i = 0; i < cards.length(); i++) {
            JSONObject cardData = cards.getJSONObject(i);

            if (!cardData.getString("med").equals("")) {
                Card card = cardRepository.findByNameAndExpansionId(cardData.getString("name"), expansionId);

                if(card == null) {
                    logger.info("No card found with name: " + cardData.getString("name"));

                    continue;
                }

                CardCollection cardCollection = this.parseCardPricing(cardData.getString("med"), card);

                cardCollectionRepository.save(cardCollection);

                logger.info("Updated price on " + card.getName() + " [" + card.getExpansion() + "] to " + cardCollection.getMintPrice());
            }
        }
    }

    private CardCollection parseCardPricing(String rawPriceData, Card actCard) {
        int price = this.convertPriceData(rawPriceData);

        CardCollection cardCollection = cardCollectionRepository.findByIdOrCreateIfNotExists(actCard.getId());
        cardCollection.setFoilPrice(price * 2);
        cardCollection.setMintPrice(price);
        cardCollection.setNearMintPrice(price);
        cardCollection.setSlightlyPlayedPrice(price);
        cardCollection.setModeratelyPlayedPrice(price);
        cardCollection.setHeavilyPlayedPrice(price);

        return cardCollection;
    }

    private int convertPriceData(String rawPriceData) {
        return (int) (Double.parseDouble(rawPriceData.replace("$", "").replace(",", "")) * 100);
    }

    private String mapExpansionName(String expansionName) {
        switch (expansionName) {
            case "Magic: The Gathering—Conspiracy":
                return "Conspiracy";
        }

        return expansionName;
    }
}
