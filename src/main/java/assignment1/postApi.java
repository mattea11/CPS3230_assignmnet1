package assignment1;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.HttpURLConnection;


public class postApi {

    public List<String> createJsonObject(List<itemInfo> items, int itemType) throws JSONException, UnsupportedEncodingException{ //String title, String description, String url, String imageUrl, String price, String date
        int act_price = 0;
        JSONObject json = new JSONObject();
        List<String> alert = new ArrayList<>();

        try {// ensures that the json object created will keep the elements in the order they are added
        Field changeMap = json.getClass().getDeclaredField("map");
        changeMap.setAccessible(true);
        changeMap.set(json, new LinkedHashMap<>());
        changeMap.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
        System.out.println(e.getMessage());
        }

        for (int i = 0; i < items.size(); i++) {    //create a jsonobject for every item passed in

            if(items.get(i).price.length() == 1){ // ensures the price passed is always 4 digits long 
                act_price = Integer.parseInt(items.get(i).price) * 1000;
            } else if(items.get(i).price.length() == 2){
                act_price = Integer.parseInt(items.get(i).price) * 100;
            } else if(items.get(i).price.length() == 3){
                act_price = Integer.parseInt(items.get(i).price) * 10;
            } else{
                act_price = Integer.parseInt(items.get(i).price);
            }

            //create the json object
            json.put("alertType", itemType);
            json.put("heading", items.get(i).title);
            json.put("description", items.get(i).description);
            json.put("url", items.get(i).url);
            json.put("imageUrl", items.get(i).image);
            json.put("postedBy", "7f84a00a-eeac-47fa-b15c-ee7e7ff9378d");
            json.put("priceInCents", act_price);

            alert.add(json.toString());
            System.out.println("~~~~" + json.toString() + "\n\n");
        }
        return alert; // return a list of all the alerts generated
    }

    public boolean postAlert(String alert) throws IOException{
        URL market_alert_um = new URL("https://api.marketalertum.com/Alert");
        HttpURLConnection connect = (HttpURLConnection)market_alert_um.openConnection(); //connect to the website

        connect.setRequestMethod("POST");
        connect.setRequestProperty("Content-Type", "application/json");
        connect.setRequestProperty("Accept", "application/json");
        connect.setDoOutput(true);

        try(OutputStream os = connect.getOutputStream()) {
            byte[] input = alert.getBytes("utf-8");
            os.write(input, 0, input.length);			
        }

        try(BufferedReader br = new BufferedReader(
        new InputStreamReader(connect.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
        System.out.println("Return code:\t" + connect.getResponseCode() + "\n");

        if(connect.getResponseCode() == 201){ // boolean return used for testing
            return true;
        }
        return false;
    }   

    public boolean deleteAlerts() throws IOException{
        URL market_alert_um = new URL("https://api.marketalertum.com/Alert?userId=7f84a00a-eeac-47fa-b15c-ee7e7ff9378d");
        HttpURLConnection connect = (HttpURLConnection)market_alert_um.openConnection(); //connect to the website

        connect.setRequestMethod("DELETE");
        connect.setRequestProperty("Content-Type", "application/json");
        connect.setRequestProperty("Accept", "application/json");
        connect.setDoOutput(true);

        System.out.println("Return code:\t" + connect.getResponseCode() + "\n");

        if(connect.getResponseCode() == 201){ // boolean return used for testing
            return true;
        }
        return false;
    } 
}
