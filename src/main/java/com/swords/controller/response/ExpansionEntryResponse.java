package com.swords.controller.response;

import java.util.ArrayList;
import java.util.List;

public class ExpansionEntryResponse {

    private ExpansionItemResponse expansion;
    private List<CardItemResponse> cardlist = new ArrayList<>();
    private int priceDivider;

    public ExpansionItemResponse getExpansion() {
        return expansion;
    }

    public void setExpansion(ExpansionItemResponse expansion) {
        this.expansion = expansion;
    }

    public List<CardItemResponse> getCardlist() {
        return cardlist;
    }
    
    public void addCard(CardItemResponse card) {
        cardlist.add(card);
    }
    
    public void setPriceDivider(int priceDivider) {
        this.priceDivider = priceDivider;
    }
    
    public int getPriceDivider() {
        return this.priceDivider;
    }
}
