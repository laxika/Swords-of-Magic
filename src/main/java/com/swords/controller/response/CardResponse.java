package com.swords.controller.response;

import com.swords.model.Card;
import java.util.HashMap;

public class CardResponse {

    private final HashMap<String, CardPrintInformationResponse> printinfo = new HashMap<>();
    private final Card data;
    
    public CardResponse(Card card) {
        this.data = card;
    }

    public void setPrintInformation(String name, CardPrintInformationResponse response) {
        getPrintinfo().put(name, response);
    }

    public HashMap<String, CardPrintInformationResponse> getPrintinfo() {
        return printinfo;
    }

    public Card getData() {
        return data;
    }
}
