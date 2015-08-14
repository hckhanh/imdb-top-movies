package com.demo.imdb.top.movies.utils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.w4ys.requestresponse.UrlInvalidException;

import java.io.IOException;

/**
 * Created by hckhanh on 11/08/2015.
 */
public class OkHttpRequest extends JsonRequest {

    public OkHttpRequest(String url) {
        super(url);
    }

    @Override
    public String getData() throws NoConnectionException, UrlInvalidException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(mUrl)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new NoConnectionException(mUrl, "No connection", e);
        }
    }

}
