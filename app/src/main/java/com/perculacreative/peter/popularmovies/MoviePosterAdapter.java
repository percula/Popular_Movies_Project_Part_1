package com.perculacreative.peter.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by peter on 7/1/16.
 */
public class MoviePosterAdapter extends ArrayAdapter<MovieItem> {

    //Note: I adapted some of this code from the Miwok application.

    /**
     * This is the constructor
     * @param context        The current context. Used to inflate the layout file.
     * @param movies       A List of location objects to display in a list
     */
    public MoviePosterAdapter(Activity context, ArrayList<MovieItem> movies) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, movies);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_movie, parent, false);
        }

        // Get the location object located at this position in the list
        MovieItem currentMovie = getItem(position);

        // Find the imageview and set the image
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185/" + currentMovie.getmPoster()).into(imageView);


        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
