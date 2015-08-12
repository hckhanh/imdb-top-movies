package com.demo.imdbtopmovies.utils;

import android.util.Log;

import com.w4ys.requestresponse.UrlInvalidException;
import com.w4ys.requestresponse.UrlTool;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by hckhanh on 11/08/2015.
 */
public class HttpUrlRequest extends JsonRequest {

    public HttpUrlRequest(String url) {
        super(url);
    }

    @Override
    public String getData() throws NoConnectionException, UrlInvalidException {
        HttpURLConnection urlConnection = null;
        InputStreamReader reader = null;

        try {
            urlConnection = (HttpURLConnection) UrlTool.openConnection(mUrl);
            reader = new InputStreamReader(urlConnection.getInputStream());
            StringBuilder stringBuilder = new StringBuilder();

            int nData;
            do {
                char[] data = new char[8000];
                nData = reader.read(data);
                if (nData > 0)
                    stringBuilder.append(data);
            } while (nData > 0);

            return stringBuilder.toString();
        } catch (IOException e) {
            throw new NoConnectionException(mUrl, "No connection", e);
        } catch (UrlInvalidException e) {
            throw new UrlInvalidException("Url invalid", mUrl);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                Log.e(getClass().getName(), "Can not close InputStreamReader", e);
            }
        }
    }

}
