package com.swords.controller.response;

import com.swords.model.Card;

public class CardResponse {

    private final Card data;
    
    public CardResponse(Card card) {
        this.data = card;
    }

    public Card getData() {
        return data;
    }
}
