package com.swords.model;

import com.swords.model.constant.RarityConstant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ExpansionCollection {

    @Id
    private String id;
    private int commonAmount = 0;
    private int uncommonAmount = 0;
    private int rareAmount = 0;
    private int mythicAmount = 0;

    public ExpansionCollection(String id) {
        this.id = id;
    }

    public int getCommonAmount() {
        return commonAmount;
    }

    public void setCommonAmount(int commonAmount) {
        this.commonAmount = commonAmount;
    }

    public int getUncommonAmount() {
        return uncommonAmount;
    }

    public void setUncommonAmount(int uncommonAmount) {
        this.uncommonAmount = uncommonAmount;
    }

    public int getRareAmount() {
        return rareAmount;
    }

    public void setRareAmount(int rareAmount) {
        this.rareAmount = rareAmount;
    }

    public int getMythicAmount() {
        return mythicAmount;
    }

    public void setMythicAmount(int mythicAmount) {
        this.mythicAmount = mythicAmount;
    }

    public int getRarityByName(String name) {
        switch (name) {
            case RarityConstant.COMMON:
                return this.commonAmount;
            case RarityConstant.UNCOMMON:
                return this.getUncommonAmount();
            case RarityConstant.RARE:
                return this.getRareAmount();
            case RarityConstant.MYTHIC_RARE:
                return this.getMythicAmount();
        }

        throw new IllegalArgumentException("No rarity found for name: " + name);
    }

    public void setRarityByName(String name, int value) {
        switch (name) {
            case RarityConstant.COMMON:
                this.commonAmount = value;
                break;
            case RarityConstant.UNCOMMON:
                this.setUncommonAmount(value);
                break;
            case RarityConstant.RARE:
                this.setRareAmount(value);
                break;
            case RarityConstant.MYTHIC_RARE:
                this.setMythicAmount(value);
                break;
            default:
                throw new IllegalArgumentException("No rarity found for name: " + name);
        }
    }
}
