package com.swords.controller.response;

import com.swords.model.Statistics;

import java.util.ArrayList;

public class StatisticsResponse {

    private ArrayList<Integer> mythic = new ArrayList<>();
    private ArrayList<Integer> rare = new ArrayList<>();
    private ArrayList<Integer> uncommon = new ArrayList<>();
    private ArrayList<Integer> common = new ArrayList<>();

    public void addDayData(Statistics statistics) {
        mythic.add(statistics.getMythic());
        rare.add(statistics.getRare());
        uncommon.add(statistics.getUncommon());
        common.add(statistics.getCommon());
    }

    public ArrayList<Integer> getMythic() {
        return mythic;
    }

    public ArrayList<Integer> getRare() {
        return rare;
    }

    public ArrayList<Integer> getUncommon() {
        return uncommon;
    }

    public ArrayList<Integer> getCommon() {
        return common;
    }
}
