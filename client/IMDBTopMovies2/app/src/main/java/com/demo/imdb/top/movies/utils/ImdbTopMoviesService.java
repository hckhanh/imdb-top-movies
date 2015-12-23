package com.demo.imdb.top.movies.utils;

import com.demo.imdb.top.movies.data.Movie;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ImdbTopMoviesService {

    @GET("/imdb_top_250")
    Call<List<Movie>> getMovies(@Query("offset") int offset);

}
