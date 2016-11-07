package com.example.android.restaurantsnearby;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.resource;

/**
 * Created by vamsi on 05-11-2016.
 */

public class RestaurantAdapter extends ArrayAdapter<Restaurants> {

    public RestaurantAdapter(Context context, ArrayList<Restaurants> Restaurant) {
        super(context, 0, Restaurant);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.restaurants_list, parent, false);
        }

        Restaurants currentItem = getItem(position);

        TextView name = (TextView) listItemView.findViewById(R.id.Restaurant_Name);
        name.setText(currentItem.getName());

        TextView rating = (TextView) listItemView.findViewById(R.id.Restaurant_rating);
        String formattedRating = formatRating(currentItem.getRating());
        rating.setText(formattedRating);

        TextView address = (TextView) listItemView.findViewById(R.id.rAddress);
        address.setText(currentItem.getAddress());

        ImageView thumbImage = (ImageView) listItemView.findViewById(R.id.thumb);
        String Url = currentItem.getUrl();
        if (TextUtils.isEmpty(Url)) {
            Picasso.with(getContext()).load(R.drawable.sample_thumb).into(thumbImage);
        } else {
            Picasso.with(getContext()).load(Url).into(thumbImage);
        }
        return listItemView;
    }

    private String formatRating(double rating) {
        DecimalFormat ratingFormat = new DecimalFormat("0.00");
        return ratingFormat.format(rating);
    }
}
