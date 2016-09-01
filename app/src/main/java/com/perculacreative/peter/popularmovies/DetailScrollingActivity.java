package com.perculacreative.peter.popularmovies;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // No need for FAB yet, but I may add it for Part 2 so just commenting this out for now
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        // Get selected movie
        MovieItem currentMovie = (MovieItem) getIntent().getParcelableExtra(MainActivity.CURRENT_MOVIE_KEY);

        // Set title
        ((CollapsingToolbarLayout) findViewById(R.id.toolbar_layout)).setTitle(currentMovie.getmTitle());


        // Set release date
        String date = currentMovie.getmRelease();
        try {
            SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date oldDate = oldFormat.parse(date);
            SimpleDateFormat newFormat = new SimpleDateFormat("MMMM F, yyyy", Locale.ENGLISH);
            date = newFormat.format(oldDate);
        } catch (Exception e) {

        }
        String release = String.format(getString(R.string.release), date);
        ((TextView) findViewById(R.id.release)).setText(release);

        // Set vote average
        String vote = String.format(getString(R.string.vote), currentMovie.getmVote());
        ((TextView) findViewById(R.id.vote)).setText(vote);

        // Set plot synopsis
        String plot = String.format(getString(R.string.plot), currentMovie.getmPlot());
        ((TextView) findViewById(R.id.plot)).setText(plot);

        // Set poster
        ImageView imageView = (ImageView) findViewById(R.id.image);
        ImageView backgroundImageView = (ImageView) findViewById(R.id.poster_background);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w342/" + currentMovie.getmPoster()).into(imageView);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w342/" + currentMovie.getmPoster()).transform(new BlurTransformation(this)).into(backgroundImageView);
    }
}
