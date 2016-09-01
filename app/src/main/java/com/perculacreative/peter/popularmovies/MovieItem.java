package com.perculacreative.peter.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by peter on 7/1/16.
 */
public class MovieItem implements Parcelable {
    private String mTitle;
    private String mPoster;
    private String mRelease;
    private String mPlot;
    private String mVote;

    public MovieItem(String title, String poster, String release, String vote, String plot) {
        mTitle = title;
        mPoster = poster;
        mRelease = release;
        mVote = vote;
        mPlot = plot;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmPoster() {
        return mPoster;
    }

    public String getmPlot() {
        return mPlot;
    }

    public String getmRelease() {
        return mRelease;
    }

    public String getmVote() {
        return mVote;
    }

    // Code to save instance state, with help from http://stackoverflow.com/questions/12503836/how-to-save-custom-arraylist-on-android-screen-rotate
    private MovieItem(Parcel parcel) {
        mTitle = parcel.readString();
        mPoster = parcel.readString();
        mRelease = parcel.readString();
        mVote = parcel.readString();
        mPlot = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mTitle);
        parcel.writeString(mPoster);
        parcel.writeString(mRelease);
        parcel.writeString(mVote);
        parcel.writeString(mPlot);
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        public MovieItem createFromParcel(Parcel parcel) {
            return new MovieItem(parcel);
        }

        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}
