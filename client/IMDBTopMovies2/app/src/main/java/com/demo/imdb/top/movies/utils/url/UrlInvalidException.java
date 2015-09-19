package com.demo.imdb.top.movies.utils.url;

/**
 * Created by hckhanh on 29/07/2015.
 */
public class UrlInvalidException extends Exception {

    private String urlString;

    public UrlInvalidException(String message, String urlString) {
        super(message);
        this.urlString = urlString;
    }

    public String getErrorUrlString() {
        return urlString;
    }

}
