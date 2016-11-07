package com.example.android.restaurantsnearby;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.restaurantsnearby.MainActivity.LOG_TAG;

/**
 * Created by vamsi on 05-11-2016.
 */

public class QueryHandler {
    private static double restaurantLatitude;
    private static double restaurantLongitude;
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        String API_KEY = "8c606c998f63ea5e91e31dd5ff1a55f7";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept", " application/json");
            urlConnection.setRequestProperty("user-key", " "+API_KEY);
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Restaurants> extractFeatureFromJson(String RestaurantJSON) {

        if (TextUtils.isEmpty(RestaurantJSON)) {
            return null;
        }

        List<Restaurants> Restaurants = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(RestaurantJSON);
            JSONArray nearby_restaurants = baseJsonResponse.getJSONArray("nearby_restaurants");

            for (int i = 0; i < nearby_restaurants.length(); i++) {

                JSONObject restaurantData = nearby_restaurants.getJSONObject(i);
                JSONObject currentRestaurant = restaurantData.getJSONObject("restaurant");
                String restaurantName = currentRestaurant.getString("name");
                JSONObject ratingList = currentRestaurant.getJSONObject("user_rating");
                JSONObject location = currentRestaurant.getJSONObject("location");
                double rating = ratingList.getDouble("aggregate_rating");
                String thumbURL = currentRestaurant.getString("thumb");
                String profileURL = currentRestaurant.getString("url");
                String rAddress = location.getString("address");
                String rLocality = location.getString("locality");
                String rCity = location.getString("city");
                restaurantLatitude = location.getDouble("latitude");
                restaurantLongitude= location.getDouble("longitude");
                Restaurants restaurant = new Restaurants(restaurantName,rating,thumbURL,profileURL,rAddress );
                Restaurants.add(restaurant);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the Restaurants JSON results", e);
        }

        return Restaurants;
    }

    public static List<Restaurants> fetchRestaurantData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Restaurants> earthquakes = extractFeatureFromJson(jsonResponse);
        return earthquakes;
    }

    public static double getRestaurantLatitude() {
        return restaurantLatitude;
    }

    public static double getRestaurantLongitude() {
        return restaurantLongitude;
    }


}
