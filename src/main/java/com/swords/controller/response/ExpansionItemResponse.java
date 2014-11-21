package com.swords.controller.response;

import com.swords.model.Expansion;
import com.swords.model.ExpansionCollection;

public class ExpansionItemResponse {

    private Expansion data;
    private ExpansionCollection collection;

    public Expansion getData() {
        return data;
    }

    public void setData(Expansion data) {
        this.data = data;
    }

    public ExpansionCollection getCollection() {
        return collection;
    }

    public void setCollection(ExpansionCollection collection) {
        this.collection = collection;
    }
}
