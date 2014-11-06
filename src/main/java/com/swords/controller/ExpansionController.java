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
            ExpansionItemResponse response = new ExpansionItemResponse();
            
            response.setData(expansion);
            response.setCollection(new CollectionItemResponse());
            
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
            Collection collection = collectionRepository.findById(card.getId());

            if (collection == null) {
                collection = new Collection();
            }
            
            response.addCard(new CardItemResponse(card, collection));
        });

        ExpansionItemResponse resp = new ExpansionItemResponse();
        
        resp.setData(expansion);
        resp.setCollection(new CollectionItemResponse());

        response.setExpansion(resp);

        return response;
    }
}
