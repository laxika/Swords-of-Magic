package com.swords.controller.response;

import java.util.ArrayList;
import java.util.List;

public class ExpansionIndexResponse {

    private final List<ExpansionItemResponse> expansionlist = new ArrayList<>();

    public List<ExpansionItemResponse> getExpansionlist() {
        return expansionlist;
    }
    
    public void addExpansion(ExpansionItemResponse expansion) {
        expansionlist.add(expansion);
    }
}
