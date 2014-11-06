package com.swords.controller.response;

import java.util.List;

public class ExpansionEntryResponse {

    private ExpansionItemResponse expansion;
    private List<CardItemResponse> cardlist;

    public ExpansionItemResponse getExpansion() {
        return expansion;
    }

    public void setExpansion(ExpansionItemResponse expansion) {
        this.expansion = expansion;
    }

    public List<CardItemResponse> getCardlist() {
        return cardlist;
    }

    public void setCardlist(List<CardItemResponse> cardlist) {
        this.cardlist = cardlist;
    }
}
