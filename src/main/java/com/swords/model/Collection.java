package com.swords.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Collection {

    @Id
    private String id;
    private int mintPrice = 0;
    private int nearMintPrice = 0;
    private int slightlyPlayedPrice = 0;
    private int moderatelyPlayedPrice = 0;
    private int heavilyPlayedPrice = 0;
    private int onlinePrice = 0;
    private int foilPrice = 0;
    private int mintAmount = 0;
    private int nearMintAmount = 0;
    private int slightlyPlayedAmount = 0;
    private int moderatelyPlayedAmount = 0;
    private int heavilyPlayedAmount = 0;
    private int onlineAmount = 0;
    private int foilAmount = 0;

    public String getId() {
        return id;
    }

    public int getMintAmount() {
        return mintAmount;
    }

    public void setMintAmount(int mintAmount) {
        this.mintAmount = mintAmount;
    }

    public int getNearMintAmount() {
        return nearMintAmount;
    }

    public void setNearMintAmount(int nearMintAmount) {
        this.nearMintAmount = nearMintAmount;
    }

    public int getSlightlyPlayedAmount() {
        return slightlyPlayedAmount;
    }

    public void setSlightlyPlayedAmount(int slightlyPlayedAmount) {
        this.slightlyPlayedAmount = slightlyPlayedAmount;
    }

    public int getModeratelyPlayedAmount() {
        return moderatelyPlayedAmount;
    }

    public void setModeratelyPlayedAmount(int moderatelyPlayedAmount) {
        this.moderatelyPlayedAmount = moderatelyPlayedAmount;
    }

    public int getHeavilyPlayedAmount() {
        return heavilyPlayedAmount;
    }

    public void setHeavilyPlayedAmount(int heavilyPlayedAmount) {
        this.heavilyPlayedAmount = heavilyPlayedAmount;
    }

    public int getOnlineAmount() {
        return onlineAmount;
    }

    public void setOnlineAmount(int onlineAmount) {
        this.onlineAmount = onlineAmount;
    }

    public int getMintPrice() {
        return mintPrice;
    }

    public void setMintPrice(int mintPrice) {
        this.mintPrice = mintPrice;
    }

    public int getNearMintPrice() {
        return nearMintPrice;
    }

    public void setNearMintPrice(int nearMintPrice) {
        this.nearMintPrice = nearMintPrice;
    }

    public int getSlightlyPlayedPrice() {
        return slightlyPlayedPrice;
    }

    public void setSlightlyPlayedPrice(int slightlyPlayedPrice) {
        this.slightlyPlayedPrice = slightlyPlayedPrice;
    }

    public int getModeratelyPlayedPrice() {
        return moderatelyPlayedPrice;
    }

    public void setModeratelyPlayedPrice(int moderatelyPlayedPrice) {
        this.moderatelyPlayedPrice = moderatelyPlayedPrice;
    }

    public int getHeavilyPlayedPrice() {
        return heavilyPlayedPrice;
    }

    public void setHeavilyPlayedPrice(int heavilyPlayedPrice) {
        this.heavilyPlayedPrice = heavilyPlayedPrice;
    }

    public int getOnlinePrice() {
        return onlinePrice;
    }

    public void setOnlinePrice(int onlinePrice) {
        this.onlinePrice = onlinePrice;
    }

    public int getFoilPrice() {
        return foilPrice;
    }

    public void setFoilPrice(int foilPrice) {
        this.foilPrice = foilPrice;
    }

    public int getFoilAmount() {
        return foilAmount;
    }

    public void setFoilAmount(int foilAmount) {
        this.foilAmount = foilAmount;
    }
}
