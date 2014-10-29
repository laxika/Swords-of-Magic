package com.swords.controller.response;

public class CardPrintInformationResponse {

    private final String title;
    private final String value;

    public CardPrintInformationResponse(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }
}
