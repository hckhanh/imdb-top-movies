package com.demo.imdb.top.movies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import com.demo.imdb.top.movies.data.Movie;
import com.demo.imdb.top.movies.adapter.MovieAdapter;
import com.demo.imdb.top.movies.utils.request.JsonRequest;
import com.demo.imdb.top.movies.utils.NoConnectionException;
import com.demo.imdb.top.movies.utils.request.OkHttpRequest;
import com.demo.imdb.top.movies.utils.url.UrlInvalidException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    static final String RANK_MOVIE_KEY = "rank";
    static final String NAME_MOVIE_KEY = "name";
    static final String CURRENT_MOVIE_LIST = "current_movies";
    static final String FIRST_REFRESH = "first_refresh";
    static final String COUNT_LIST = "count_list";

    private static final int NO_DATA = 0;
    private static final int INVALID_JSON_DATA = -1;
    private static final int INVALID_JSON_ITEM = -2;
    private static final int INVALID_URL = -3;
    private static final int INVALID_NO_CONNECTION = -4;

    MovieAdapter movieAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    CoordinatorLayout mainLayout;
    int nCountList = 0;
    boolean isFirstRefresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = (CoordinatorLayout) findViewById(R.id.main_layout);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        ListView moviesList = (ListView) findViewById(R.id.top_movies_list);
        String[] rankColors = this.getResources().getStringArray(R.array.rank_colors);

        if (savedInstanceState != null) {
            movieAdapter = new MovieAdapter(this, rankColors, savedInstanceState.getSerializable(CURRENT_MOVIE_LIST));
            isFirstRefresh = savedInstanceState.getBoolean(FIRST_REFRESH);
            nCountList = savedInstanceState.getInt(COUNT_LIST);
        } else {
            movieAdapter = new MovieAdapter(this, rankColors);
        }

        swipeRefreshLayout.setColorSchemeResources(
                R.color.swipe_color_1, R.color.swipe_color_2,
                R.color.swipe_color_3, R.color.swipe_color_4
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMovieData();
            }
        });

        moviesList.setAdapter(movieAdapter);

        if (BuildConfig.DEBUG) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                            | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                            | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                            | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            );
        }

        if (savedInstanceState == null)
            fetchMovieData();
    }

    void fetchMovieData() {
        MovieFetchJson movieFetchJson = new MovieFetchJson();
        movieFetchJson.execute(getTopMovieUrl());
    }

    private String getTopMovieUrl() { // localhost: 10.0.2.2
        return "http://code2learn.me/imdb_top_250?offset=" + nCountList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            fetchMovieData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(CURRENT_MOVIE_LIST, movieAdapter.getListMovie());
        outState.putInt(COUNT_LIST, nCountList);
        outState.putBoolean(FIRST_REFRESH, isFirstRefresh);
    }

    class MovieFetchJson extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (!swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                });
            }
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Integer doInBackground(String... params) {
            String jsonData;
            try {
                JsonRequest jsonUrlRequest = new OkHttpRequest(params[0]);
                jsonData = jsonUrlRequest.getData();
            } catch (NoConnectionException e) {
                Log.e(getClass().getName(), "No connection: " + e.getUrl(), e);
                return INVALID_NO_CONNECTION;
            } catch (UrlInvalidException e) {
                Log.e(getClass().getName(), "Invalid url: " + e.getErrorUrlString(), e);
                return INVALID_URL;
            }

            JSONArray movieJsonArray;
            try {
                movieJsonArray = new JSONArray(jsonData);
            } catch (JSONException e) {
                Log.e(getClass().getName(), "Can not read movie json data", e);
                return INVALID_JSON_DATA;
            }


            int size = movieJsonArray.length();
            if (size > 0) {
                movieAdapter.clear();
                for (int i = 0; i < size; i++) {
                    try {
                        JSONObject movieJsonObj = movieJsonArray.getJSONObject(i);
                        Movie movie = new Movie(movieJsonObj.getInt(RANK_MOVIE_KEY),
                                movieJsonObj.getString(NAME_MOVIE_KEY));

                        movieAdapter.addItem(movie);
                    } catch (JSONException e) {
                        Log.e(getLocalClassName(), "Can not read movie json item", e);
                        return INVALID_JSON_ITEM;
                    }
                }
                nCountList += 20;
                return size;
            } else
                return NO_DATA;
        }

        @Override
        protected void onPostExecute(Integer exitCode) {
            super.onPostExecute(exitCode);
            swipeRefreshLayout.setRefreshing(false);

            switch (exitCode) {
                case NO_DATA:
                    Snackbar.make(mainLayout, "This is the end of the list.", Snackbar.LENGTH_LONG)
                            .setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            })
                            .show();
                    break;
                case INVALID_JSON_DATA:
                case INVALID_JSON_ITEM:
                case INVALID_URL:
                    movieAdapter.notifyDataSetInvalidated();
                    Snackbar.make(mainLayout, "Something went wrong!", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Refresh", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fetchMovieData();
                                }
                            })
                            .show();
                    break;
                case INVALID_NO_CONNECTION:
                    movieAdapter.notifyDataSetInvalidated();
                    Snackbar.make(mainLayout, "No connection", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fetchMovieData();
                                }
                            })
                            .show();
                    break;
                default:
                    movieAdapter.notifyDataSetChanged();
                    if (isFirstRefresh) {
                        Snackbar.make(mainLayout, "Swipe down to refresh", Snackbar.LENGTH_LONG)
                                .setAction("Dismiss", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                })
                                .show();
                        isFirstRefresh = false;
                    }
            }

        }

    }

}
