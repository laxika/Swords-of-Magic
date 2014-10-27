package com.swords.util;

import org.json.JSONArray;

public class JSONUtils {

    public static String[] jsonArrayToStringArray(JSONArray array) {
        String[] stringArray = new String[array.length()];
        
        for (int i = 0; i < array.length(); i++) {
            stringArray[i] = array.getString(i);
        }

        return stringArray;
    }
}
