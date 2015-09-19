package com.demo.imdb.top.movies.utils.url;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by hckhanh on 29/07/2015.
 *
 */
public class UrlTool {

    public static URLConnection openConnection(String urlString) throws IOException, UrlInvalidException {
        try {
            URL url = new URL(urlString);
            return url.openConnection();
        } catch (MalformedURLException e) {
            throw new UrlInvalidException("Invalid url: " + urlString, urlString);
        }
    }
}
