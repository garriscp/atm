package com.backbase.atm;

/**
 * Created by admin on 8/31/15.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Service {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);

            //here we have to clean the JSON - the JSON from this web service is not formatted correctly
            jsonText = cleanupJsonText(jsonText);
            JSONArray json = new JSONArray(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static String cleanupJsonText (String brokenJSON) {
        //just a few extra characters in the beginning - let's start the string when the opening bracket of the JSONArray starts
        String fixedJson = brokenJSON.substring(brokenJSON.indexOf("["));
        return fixedJson;
    }

    public static ArrayList<ATM> retrieveATMs(HashMap<String,String> filter) throws IOException {
        //The method to retrieve ATMs is shared across all routes

        JSONArray json = readJsonFromUrl("https://www.ing.nl/api/locator/atms/");

        ArrayList<ATM> atms = new ArrayList<ATM>();

        for (int i = 0; i < json.length(); i++) {
            JSONObject atm = json.getJSONObject(i);
            ATM myAtm = new ATM();
            Address myAddress = myAtm.getAddress();
            JSONObject address = atm.getJSONObject("address");
            myAtm.setType(atm.getString("type"));

            myAddress.setCity(address.getString("city"));
            myAddress.setStreet(address.getString("street"));
            myAddress.setHouseNumber(address.getString("housenumber"));
            myAddress.setPostalCode(address.getString("postalcode"));


            GeoLocation myGeo = myAddress.getGeoLocation();
            JSONObject geo = address.getJSONObject("geoLocation");
            myGeo.setLat(geo.getString("lat"));
            myGeo.setLng(geo.getString("lng"));


            //I am only checking the filter in the end, and then deciding to add the ATM or not
            //This isn't the most efficient time-wise, but it makes the code dry and clean
            //Would be open to exploring different ways to refactor this, however
            if (filter.get("property").equalsIgnoreCase("all")) {
                atms.add(0,myAtm);
            } else if (filter.get("property").equalsIgnoreCase("city")) {
                if (myAddress.getCity().equalsIgnoreCase(filter.get("value"))) {
                    atms.add(0,myAtm);
                }
            } else if (filter.get("property").equalsIgnoreCase("type")) {
                if (myAtm.getType().equalsIgnoreCase(filter.get("value"))) {
                    atms.add(0,myAtm);
                }
            }

        }

        return atms;
    }

    //define some public methods which are exposed for camel - these map to different routes
    //I pass a hashmap properties object to define if there is a filter, or if we want all ATMs

    public static ArrayList<ATM> getATMsByCity(String city) throws IOException {
        HashMap<String, String> hmap = new HashMap<String, String>();
        hmap.put("property","city");
        hmap.put("value",city);
        return retrieveATMs(hmap);
    }

    public static ArrayList<ATM> getATMs() throws IOException {
        HashMap<String, String> hmap = new HashMap<String, String>();
        hmap.put("property","all");
        hmap.put("value","");
        return retrieveATMs(hmap);
    }

    public static ArrayList<ATM> getATMsByType(String type) throws IOException {
        HashMap<String, String> hmap = new HashMap<String, String>();
        hmap.put("property","type");
        hmap.put("value",type);
        return retrieveATMs(hmap);
    }


}
