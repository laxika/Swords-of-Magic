package com.swords.controller.response;

import java.util.List;

public class ExpansionIndexResponse {

    private List<ExpansionItemResponse> expansionlist;

    public List<ExpansionItemResponse> getExpansionlist() {
        return expansionlist;
    }

    public void setExpansionlist(List<ExpansionItemResponse> expansionlist) {
        this.expansionlist = expansionlist;
    }
}
