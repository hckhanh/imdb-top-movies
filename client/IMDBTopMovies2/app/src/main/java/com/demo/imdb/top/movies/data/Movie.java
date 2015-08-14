package com.demo.imdb.top.movies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hckhanh on 01/08/2015.
 */
public class Movie implements Parcelable {

    private int rank;
    private String name;

    public Movie(int rank, String name) {
        this.rank = rank;
        this.name = name;
    }

    private Movie(Parcel in) {
        rank = in.readInt();
        name = in.readString();
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rank);
        dest.writeString(name);
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
