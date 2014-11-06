package com.swords.controller.response;

public class CollectionItemResponse {

    private int mythicCount = 0;
    private int rareCount = 0;
    private int uncommonCount = 0;
    private int commonCount = 0;

    public int getMythicCount() {
        return mythicCount;
    }

    public void setMythicCount(int mythicCount) {
        this.mythicCount = mythicCount;
    }

    public int getRareCount() {
        return rareCount;
    }

    public void setRareCount(int rareCount) {
        this.rareCount = rareCount;
    }

    public int getUncommonCount() {
        return uncommonCount;
    }

    public void setUncommonCount(int uncommonCount) {
        this.uncommonCount = uncommonCount;
    }

    public int getCommonCount() {
        return commonCount;
    }

    public void setCommonCount(int commonCount) {
        this.commonCount = commonCount;
    }
}
