package com.demo.imdbtopmovies.utils;

import com.w4ys.requestresponse.UrlInvalidException;

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
