package com.demo.imdb.top.movies.utils.request;

import com.demo.imdb.top.movies.utils.NoConnectionException;
import com.demo.imdb.top.movies.utils.url.UrlInvalidException;

/**
 * Created by hckhanh on 11/08/2015.
 */
public abstract class JsonRequest {

    String mUrl;

    public JsonRequest(String url) {
        this.mUrl = url;
    }

    public abstract String getData() throws NoConnectionException, UrlInvalidException, UrlInvalidException;

}
