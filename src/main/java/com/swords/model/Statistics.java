package com.swords.model;

import com.swords.model.constant.RarityConstant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document
public class Statistics {

    @Id
    private long id;
    private int mythic = 0;
    private int rare = 0;
    private int uncommon = 0;
    private int common = 0;

    public Statistics(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public int getMythic() {
        return mythic;
    }

    public void setMythic(int mythic) {
        this.mythic = mythic;
    }

    public int getRare() {
        return rare;
    }

    public void setRare(int rare) {
        this.rare = rare;
    }

    public int getUncommon() {
        return uncommon;
    }

    public void setUncommon(int uncommon) {
        this.uncommon = uncommon;
    }

    public int getCommon() {
        return common;
    }

    public void setCommon(int common) {
        this.common = common;
    }

    public void setRarityStatisticsByName(String name, int value) {
        switch (name) {
            case RarityConstant.COMMON:
            case RarityConstant.BASIC_LAND:
                this.setCommon(value);
                break;
            case RarityConstant.UNCOMMON:
                this.setUncommon(value);
                break;
            case RarityConstant.RARE:
                this.setRare(value);
                break;
            case RarityConstant.MYTHIC_RARE:
                this.setMythic(value);
                break;
            default:
                throw new IllegalArgumentException("No rarity found for name: " + name);
        }
    }

    public int getRarityByName(String name) {
        switch (name) {
            case RarityConstant.COMMON:
            case RarityConstant.BASIC_LAND:
                return this.getCommon();
            case RarityConstant.UNCOMMON:
                return this.getUncommon();
            case RarityConstant.RARE:
                return this.getRare();
            case RarityConstant.MYTHIC_RARE:
                return this.getMythic();
        }

        throw new IllegalArgumentException("No rarity found for name: " + name);
    }
}
