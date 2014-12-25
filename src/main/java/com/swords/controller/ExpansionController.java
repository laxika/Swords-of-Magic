package com.swords.controller;

import com.swords.controller.response.CardItemResponse;
import com.swords.controller.response.CollectionItemResponse;
import com.swords.controller.response.ExpansionEntryResponse;
import com.swords.controller.response.ExpansionIndexResponse;
import com.swords.controller.response.ExpansionItemResponse;
import com.swords.model.Card;
import com.swords.model.CardCollection;
import com.swords.model.Expansion;
import com.swords.model.ExpansionCollection;
import com.swords.model.repository.CardRepository;
import com.swords.model.repository.CardCollectionRepository;
import com.swords.model.repository.ExpansionCollectionRepository;
import com.swords.model.repository.ExpansionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExpansionController {

    @Autowired
    private ExpansionRepository expansionRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardCollectionRepository collectionRepository;
    @Autowired
    private ExpansionCollectionRepository expansionCollectionRepository;
    @Value("${swords.price.divider}")
    private double priceDivider;
    @Value("${swords.price.unit}")
    private String priceUnit;

    @RequestMapping("/expansion/index")
    public String expansionIndexTemplate() {
        return "expansion/index";
    }

    @ResponseBody
    @RequestMapping(value = "/expansion/data", produces = "application/json; charset=utf-8")
    public ExpansionIndexResponse expansionIndexData() {
        List<Expansion> expansionlist = expansionRepository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC, "releaseDate")));

        ExpansionIndexResponse indexResponse = new ExpansionIndexResponse();

        expansionlist.stream().forEach((expansion) -> {
            ExpansionCollection expansionCollection = expansionCollectionRepository.findByIdOrCreateIfNotExists(expansion.getId());

            ExpansionItemResponse response = new ExpansionItemResponse();

            response.setData(expansion);
            response.setCollection(expansionCollection);

            indexResponse.addExpansion(response);
        });

        return indexResponse;
    }

    @RequestMapping("/expansion/entry")
    public String expansionTemplate() {
        return "expansion/entry";
    }

    @ResponseBody
    @RequestMapping(value = "/expansion/data/{setname}", produces = "application/json; charset=utf-8")
    public ExpansionEntryResponse expansionData(@PathVariable String setname) {
        Expansion expansion = expansionRepository.findById(setname);

        List<Card> cardlist = cardRepository.findByExpansionId(setname);

        ExpansionEntryResponse response = new ExpansionEntryResponse();

        cardlist.stream().forEach((card) -> {
            CardCollection collection = collectionRepository.findById(card.getId());

            if (collection == null) {
                collection = new CardCollection(card.getId());
            }

            response.addCard(new CardItemResponse(card, collection));
        });

        ExpansionCollection expansionCollection = expansionCollectionRepository.findById(expansion.getId());

        ExpansionItemResponse resp = new ExpansionItemResponse();

        resp.setData(expansion);
        resp.setCollection(expansionCollection);

        response.setExpansion(resp);
        response.setPriceDivider(priceDivider);
        response.setPriceUnit(priceUnit);

        return response;
    }
}
