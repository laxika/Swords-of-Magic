package com.swords.model;

import com.swords.model.constant.CollectionConstant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CardCollection {

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

    public CardCollection(String id) {
        this.id = id;
    }

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

    public int getAmountByName(String name) {
        switch (name) {
            case CollectionConstant.MINT_AMOUNT:
                return this.mintAmount;
            case CollectionConstant.NEAR_MINT_AMOUNT:
                return this.nearMintAmount;
            case CollectionConstant.SLIGHTLY_PLAYED_AMOUNT:
                return this.slightlyPlayedAmount;
            case CollectionConstant.MODERATELY_PLAYED_AMOUNT:
                return this.moderatelyPlayedAmount;
            case CollectionConstant.HEAVILY_PLAYED_AMOUNT:
                return this.heavilyPlayedAmount;
            case CollectionConstant.FOIL_AMOUNT:
                return this.foilAmount;
            case CollectionConstant.ONLINE_AMOUNT:
                return this.onlineAmount;
        }

        throw new IllegalArgumentException();
    }

    public void setAmountByName(String name, int value) {

        switch (name) {
            case CollectionConstant.MINT_AMOUNT:
                this.mintAmount = value;
                break;
            case CollectionConstant.NEAR_MINT_AMOUNT:
                this.nearMintAmount = value;
                break;
            case CollectionConstant.SLIGHTLY_PLAYED_AMOUNT:
                this.slightlyPlayedAmount = value;
                break;
            case CollectionConstant.MODERATELY_PLAYED_AMOUNT:
                this.moderatelyPlayedAmount = value;
                break;
            case CollectionConstant.HEAVILY_PLAYED_AMOUNT:
                this.heavilyPlayedAmount = value;
                break;
            case CollectionConstant.FOIL_AMOUNT:
                this.foilAmount = value;
                break;
            case CollectionConstant.ONLINE_AMOUNT:
                this.onlineAmount = value;
                break;
            default:
                throw new IllegalArgumentException("No collection amount found for name: " + name);
        }
    }
}
