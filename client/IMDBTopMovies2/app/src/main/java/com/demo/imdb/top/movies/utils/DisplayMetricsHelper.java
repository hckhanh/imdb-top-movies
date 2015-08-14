package com.demo.imdb.top.movies.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by hckhanh on 10/08/2015.
 */
public class DisplayMetricsHelper {

    DisplayMetrics displayMetrics;

    public DisplayMetricsHelper(Context context) {
        displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    }

    public float getDensity() {
        return displayMetrics.density;
    }

    public int getDensityDpi() {
        return displayMetrics.densityDpi;
    }

    public float convertDpToPx(int dp) {
        return displayMetrics.density * dp;
    }

}
