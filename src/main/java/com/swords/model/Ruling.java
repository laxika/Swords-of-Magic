package com.swords.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Ruling {

    private String date = "";
    private String text = "";

    public Ruling(String date, String text) {
        this.date = date;
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }
}
