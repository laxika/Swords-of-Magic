package com.swords.controller.response;

import com.swords.model.Expansion;

public class ExpansionItemResponse {

    private Expansion data;
    private CollectionItemResponse collection;

    public Expansion getData() {
        return data;
    }

    public void setData(Expansion data) {
        this.data = data;
    }

    public CollectionItemResponse getCollection() {
        return collection;
    }

    public void setCollection(CollectionItemResponse collection) {
        this.collection = collection;
    }
}
