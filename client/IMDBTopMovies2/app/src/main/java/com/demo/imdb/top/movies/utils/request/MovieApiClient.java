package com.demo.imdb.top.movies.utils.request;

import com.demo.imdb.top.movies.data.Movie;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * Created by hoang on 12/12/2015.
 */
@Rest(rootUrl = "http://code2learn.me", converters = {GsonHttpMessageConverter.class})
public interface MovieApiClient {

    @Get("/imdb_top_250?offset={offset}")
    Movie[] getMovie(int offset);

}
