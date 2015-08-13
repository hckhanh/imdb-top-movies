package com.demo.imdbtopmovies.data;

/**
 * Created by hckhanh on 01/08/2015.
 */
public class Movie {

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

}
