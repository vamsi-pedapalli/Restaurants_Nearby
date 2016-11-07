package com.example.android.restaurantsnearby;

import static android.R.attr.id;

/**
 * Created by vamsi on 05-11-2016.
 */

public class Restaurants {
    private String name;
    private double rating;
    private String Url;
    private String profileURL;
    private String address;
    private String locality;
    private String city;

    public Restaurants(String Cname, double Crating, String url) {
        name = Cname;
        rating = Crating;
        Url = url;
    }

    public Restaurants(String Cname, double Crating) {
        name = Cname;
        rating = Crating;
    }

    public Restaurants(String Cname, double Crating, String CThumbURL, String CProfileURL) {
        name = Cname;
        rating = Crating;
        Url = CThumbURL;
        profileURL = CProfileURL;
    }

    public Restaurants(String Cname, double Crating, String CThumbURL, String CProfileURL, String CAddress ) {
        name = Cname;
        rating = Crating;
        Url = CThumbURL;
        profileURL = CProfileURL;
        address=CAddress;

    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getUrl() {
        return Url;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getLocality() {
        return locality;
    }
}
