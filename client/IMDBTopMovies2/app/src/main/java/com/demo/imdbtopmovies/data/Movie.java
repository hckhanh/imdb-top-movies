package com.demo.imdbtopmovies.data;

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

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param out  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(rank);
        out.writeString(name);
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

    private Movie(Parcel in) {
        rank = in.readInt();
        name = in.readString();
    }
}
