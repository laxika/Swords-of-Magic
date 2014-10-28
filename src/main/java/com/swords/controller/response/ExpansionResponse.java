package com.swords.controller.response;

import com.swords.model.Card;
import com.swords.model.Expansion;
import java.util.List;

public class ExpansionResponse {

    private Expansion expansion;
    private List<Card> cardlist;

    public Expansion getExpansion() {
        return expansion;
    }

    public void setExpansion(Expansion expansion) {
        this.expansion = expansion;
    }

    public List<Card> getCardlist() {
        return cardlist;
    }

    public void setCardlist(List<Card> cardlist) {
        this.cardlist = cardlist;
    }
}
