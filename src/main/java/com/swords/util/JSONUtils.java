package com.swords.util;

import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {

    public static String[] jsonArrayToStringArray(JSONArray array) {
        String[] stringArray = new String[array.length()];

        for (int i = 0; i < array.length(); i++) {
            stringArray[i] = array.getString(i);
        }

        return stringArray;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            
            String jsonText = readAll(rd);
            
            JSONArray json = new JSONArray(jsonText);
            
            return json;
        }
    }
}
