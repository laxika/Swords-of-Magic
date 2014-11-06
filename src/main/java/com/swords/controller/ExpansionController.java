package com.swords.controller;

import com.swords.controller.response.CardItemResponse;
import com.swords.controller.response.CollectionItemResponse;
import com.swords.controller.response.ExpansionEntryResponse;
import com.swords.controller.response.ExpansionIndexResponse;
import com.swords.controller.response.ExpansionItemResponse;
import com.swords.model.Card;
import com.swords.model.Collection;
import com.swords.model.Expansion;
import com.swords.model.repository.CardRepository;
import com.swords.model.repository.CollectionRepository;
import com.swords.model.repository.ExpansionRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CollectionRepository collectionRepository;

    @RequestMapping("/expansion/template")
    public String expansionIndexTemplate() {
        return "expansion/index";
    }

    @ResponseBody
    @RequestMapping(value = "/expansion/data", produces = "application/json; charset=utf-8")
    public ExpansionIndexResponse expansionIndexData() {
        List<Expansion> expansionlist = expansionRepository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC, "releaseDate")));

        ArrayList<ExpansionItemResponse> expansionResponseHolder = new ArrayList<>();
        
        for (Expansion expansion : expansionlist) {
            ExpansionItemResponse response = new ExpansionItemResponse();
            
            response.setData(expansion);
            response.setCollection(new CollectionItemResponse());
            expansionResponseHolder.add(response);
        }
        
        ExpansionIndexResponse indexResponse = new ExpansionIndexResponse();
        indexResponse.setExpansionlist(expansionResponseHolder);

        return indexResponse;
    }

    @RequestMapping("/expansion/specific")
    public String expansionTemplate() {
        return "expansion/entry";
    }

    @ResponseBody
    @RequestMapping(value = "/expansion/data/{setname}", produces = "application/json; charset=utf-8")
    public ExpansionEntryResponse expansionData(@PathVariable String setname) {
        //TODO: move this to some cache or something, at least cache the static objects etc...
        Expansion expansion = expansionRepository.findById(setname);

        List<Card> cardlist = cardRepository.findByExpansionId(setname);

        ArrayList<CardItemResponse> cardResponseHolder = new ArrayList<>();

        //TODO: make a darn factory out of this, also try to cache them!
        for (Card card : cardlist) {
            Collection collection = collectionRepository.findById(card.getId());

            if (collection == null) {
                collection = new Collection();
            }

            CardItemResponse cardResponse = new CardItemResponse(card, collection);

            //Here we can set the collection/pricing/ruling data lately.
            cardResponseHolder.add(cardResponse);
        }

        ExpansionEntryResponse response = new ExpansionEntryResponse();

        ExpansionItemResponse resp = new ExpansionItemResponse();
        resp.setData(expansion);

        response.setExpansion(resp);
        response.setCardlist(cardResponseHolder);

        return response;
    }
}
