package com.swords.controller.response;

import com.swords.model.Card;
import com.swords.model.Collection;

public class CardResponse {

    private final Card data;
    private final Collection collection;
    
    public CardResponse(Card card, Collection collection) {
        this.data = card;
        this.collection = collection;
    }

    public Card getData() {
        return data;
    }

    public Collection getCollection() {
        return collection;
    }
}
