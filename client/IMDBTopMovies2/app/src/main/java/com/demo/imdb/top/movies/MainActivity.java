package com.demo.imdb.top.movies;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import com.demo.imdb.top.movies.adapter.MovieAdapter;
import com.demo.imdb.top.movies.data.Movie;
import com.demo.imdb.top.movies.utils.ConnectionUtils;
import com.demo.imdb.top.movies.utils.MovieApiClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import java.io.Serializable;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends AppCompatActivity {

    private static final int NO_DATA = 0;
    private static final int INVALID_JSON_DATA = -1;
    private static final int INVALID_NO_CONNECTION = -4;

    MovieAdapter movieAdapter;

    @ViewById(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById(R.id.main_layout)
    CoordinatorLayout mainLayout;

    @InstanceState
    int nCountList = 0;

    @InstanceState
    boolean isFirstRefresh = true;

    @InstanceState
    Serializable currentMovieList;

    @RestService
    MovieApiClient movieApiClient;

    @AfterViews
    protected void afterInitViews() {
        ListView moviesList = (ListView) findViewById(R.id.top_movies_list);
        String[] rankColors = this.getResources().getStringArray(R.array.rank_colors);

        if (currentMovieList != null)
            movieAdapter = new MovieAdapter(this, rankColors, currentMovieList);
        else
            movieAdapter = new MovieAdapter(this, rankColors);

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

        if (currentMovieList == null)
            fetchMovieData();
    }

    @OptionsItem(R.id.action_refresh)
    void fetchMovieData() {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }

        if (ConnectionUtils.isConnected(this))
            downloadData();
        else
            showResult(INVALID_NO_CONNECTION);
    }

    @Background
    protected void downloadData() {
        int resultCode;
        try {
            Movie[] movies = movieApiClient.getMovie(nCountList);

            if (movies.length > 0) {
                movieAdapter.clear();
                movieAdapter.addItems(movies);
                nCountList += 20;
                resultCode = movies.length;
            } else
                resultCode =  NO_DATA;
        } catch (RestClientException e) {
            resultCode =  INVALID_JSON_DATA;
        }

        showResult(resultCode);
    }

    @UiThread
    protected void showResult(int exitCode) {
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
