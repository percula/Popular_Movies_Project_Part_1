package com.perculacreative.peter.popularmovies;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GridFragment extends Fragment {

    private MoviePosterAdapter mMovieAdapter;
    private ArrayList<MovieItem> mMovieList = new ArrayList<MovieItem>();

    // IMPORTANT! REMOVE THIS KEY PRIOR TO GITHUB UPLOAD AND ADD BACK BEFORE COMPILING
    private String API_KEY_LABEL = "api_key";
    private String API_KEY = "2f8f597b36b68795c36c8abf42fc7016";

    public GridFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_grid, container, false);

        mMovieAdapter = new MoviePosterAdapter(getActivity(), mMovieList);

        // Get a reference to the ListView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setAdapter(mMovieAdapter);

        FetchMoviesTask moviesTask = new FetchMoviesTask();
        moviesTask.execute();

        return rootView;
    }

    public class FetchMoviesTask extends AsyncTask<Void, Void, String[]> {

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        /**
         * Parses JSON data and returns a string array for the list adapter
         */
        private String[] getMovieDataFromJson(String movieJSONStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String MV_RESULTS = "results";
            final String MV_TITLE = "title";
            final String MV_POSTER = "poster_path";
            final String MV_RELEASE = "release_date";
            final String MV_VOTE = "vote_average";
            final String MV_PLOT = "overview";


//            final String GB_INFO = "volumeInfo";

            JSONObject movieJson = new JSONObject(movieJSONStr);
            JSONArray movieArray = movieJson.getJSONArray(MV_RESULTS);

            //Determine number of returned results
            int numReturnedResults = movieArray.length();

            //If no results, return error string array
            if (numReturnedResults == 0) {
                String[] resultStrs = new String[1];
                resultStrs[0] = "No results found. Please try again.";
                return resultStrs;
            }

            //Create string array with length of returned results
            String[] resultStrs = new String[numReturnedResults];

            for (int i = 0; i < movieArray.length(); i++) {
                // Get the JSON object representing the current movie
                JSONObject currentMovie = movieArray.getJSONObject(i);

                // Get poster urls
                String title = currentMovie.getString(MV_TITLE);
                String poster = currentMovie.getString(MV_POSTER);
                String release = currentMovie.getString(MV_RELEASE);
                String vote = currentMovie.getString(MV_VOTE);
                String plot = currentMovie.getString(MV_PLOT);


                mMovieList.add(new MovieItem(title, poster, release, vote, plot));

            }

            for (String s : resultStrs) {
                Log.v(LOG_TAG, "Book: " + s);
            }
            return resultStrs;
        }

        @Override
        protected String[] doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String moviesJsonStr = null;

            try {
                final String FORECAST_BASE_URL =
                        "http://api.themoviedb.org/3/movie/popular?";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY_LABEL,API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to Google Books API, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.v("new line", line);
                }

                if (buffer.length() == 0) {
                    return null;
                }
                moviesJsonStr = buffer.toString();

                Log.v(LOG_TAG, "JSON String: " + moviesJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(moviesJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            mMovieAdapter.notifyDataSetChanged();
        }
    }

}
