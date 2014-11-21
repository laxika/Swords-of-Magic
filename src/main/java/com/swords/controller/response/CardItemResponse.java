package com.swords.controller.response;

import com.swords.model.Card;
import com.swords.model.CardCollection;

public class CardItemResponse {

    private final Card data;
    private final CardCollection collection;
    
    public CardItemResponse(Card card, CardCollection collection) {
        this.data = card;
        this.collection = collection;
    }

    public Card getData() {
        return data;
    }

    public CardCollection getCollection() {
        return collection;
    }
}
