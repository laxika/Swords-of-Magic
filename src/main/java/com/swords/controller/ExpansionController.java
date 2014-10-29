package com.swords.controller;

import com.swords.controller.response.CardPrintInformationResponse;
import com.swords.controller.response.CardResponse;
import com.swords.controller.response.ExpansionResponse;
import com.swords.model.Card;
import com.swords.model.Expansion;
import com.swords.model.repository.CardRepository;
import com.swords.model.repository.ExpansionRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

@Controller
public class ExpansionController {

    @Autowired
    private ExpansionRepository expansionRepository;
    @Autowired
    private CardRepository cardRepository;

    @RequestMapping("/expansion/template")
    public String expansionIndexTemplate() {
        return "expansion/indextemplate";
    }

    @ResponseBody
    @RequestMapping(value = "/expansion/data", produces = "application/json; charset=utf-8")
    public List<Expansion> expansionIndexData() {
        return expansionRepository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC, "releaseDate")));
    }

    @RequestMapping("/expansion/specific")
    public String expansionTemplate() {
        return "expansion/template";
    }

    @ResponseBody
    @RequestMapping(value = "/expansion/data/{setname}", produces = "application/json; charset=utf-8")
    public ExpansionResponse expansionData(@PathVariable String setname) {
        //TODO: move this to some cache or something, at least cache the static objects etc...
        Expansion expansion = expansionRepository.findById(setname);

        List<Card> cardlist = cardRepository.findByExpansionId(setname);

        ArrayList<CardResponse> cardResponseHolder = new ArrayList<>();
        
        //TODO: make a darn factory out of this, also try to cache them!
        for (Card card : cardlist) {
            CardResponse cardResponse = new CardResponse(card);

            cardResponse.setPrintInformation("name", new CardPrintInformationResponse("Name", card.getName()));
            cardResponse.setPrintInformation("rarity", new CardPrintInformationResponse("Rarity", card.getRarity()));
            cardResponse.setPrintInformation("artist", new CardPrintInformationResponse("Artist", card.getArtist()));
            
            if(card.getNumber() != null) {
                cardResponse.setPrintInformation("number", new CardPrintInformationResponse("Expansion number", card.getNumber()));
            }
            
            if(card.getPower() != null) {
                cardResponse.setPrintInformation("power", new CardPrintInformationResponse("Power", card.getPower()));
            }
            
            if(card.getToughness()!= null) {
                cardResponse.setPrintInformation("toughness", new CardPrintInformationResponse("Toughness", card.getToughness()));
            }
            
            if(card.getLayout()!= null) {
                cardResponse.setPrintInformation("layout", new CardPrintInformationResponse("Layout", card.getLayout()));
            }

            //card.manacost.replace(/\{(\w+)\}/ig, '<img src="http://mtgimage.com/symbol/mana/$1/16.gif" alt="G mana"/>');
            if (card.getManacost() != null && !card.getManacost().isEmpty()) {
                String finalManacost = card.getManacost() + " - <img src=\"http://mtgimage.com/symbol/mana/" + card.getCmc() + "/16.gif\" alt=\"" + card.getCmc() + " mana\"/>";

                cardResponse.setPrintInformation("manacost", new CardPrintInformationResponse("Manacost", finalManacost));
            }
            
            if(card.getColor() != null && card.getColor().length > 0) {
                cardResponse.setPrintInformation("color", new CardPrintInformationResponse("Color", StringUtils.join(card.getColor(), ", ")));
            } else {
                cardResponse.setPrintInformation("color", new CardPrintInformationResponse("Color", "Colorless"));
            }
            
            if(card.getSubtypes() != null && card.getSubtypes().length > 0) {
                cardResponse.setPrintInformation("type", new CardPrintInformationResponse("Type", StringUtils.join(card.getTypes(), ", ") + " — " + StringUtils.join(card.getSubtypes(), ", ")));
            } else {
                cardResponse.setPrintInformation("type", new CardPrintInformationResponse("Type", StringUtils.join(card.getTypes(), ", ")));
            }

            cardResponseHolder.add(cardResponse);
        }

        ExpansionResponse response = new ExpansionResponse();
        response.setExpansion(expansion);
        response.setCardlist(cardResponseHolder);

        return response;
    }
}
