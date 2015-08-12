package com.demo.imdbtopmovies.utils;

import java.io.IOException;

/**
 * Created by hckhanh on 11/08/2015.
 */
public class NoConnectionException extends IOException {

    String mUrl;

    public NoConnectionException(String url, String message, Throwable cause) {
        super(message, cause);
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

}
