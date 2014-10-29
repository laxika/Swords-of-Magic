package com.swords.controller.response;

import com.swords.model.Expansion;
import java.util.List;

public class ExpansionResponse {

    private Expansion expansion;
    private List<CardResponse> cardlist;

    public Expansion getExpansion() {
        return expansion;
    }

    public void setExpansion(Expansion expansion) {
        this.expansion = expansion;
    }

    public List<CardResponse> getCardlist() {
        return cardlist;
    }

    public void setCardlist(List<CardResponse> cardlist) {
        this.cardlist = cardlist;
    }
}
