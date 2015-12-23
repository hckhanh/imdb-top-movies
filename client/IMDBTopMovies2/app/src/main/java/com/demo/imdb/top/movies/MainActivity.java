package com.demo.imdb.top.movies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import com.demo.imdb.top.movies.adapter.MovieAdapter;
import com.demo.imdb.top.movies.data.Movie;
import com.demo.imdb.top.movies.utils.ImdbTopMoviesService;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    static final String COUNT_LIST = "count_list";
    static final String FIRST_REFRESH = "first_refresh";
    static final String CURRENT_MOVIE_LIST = "current_movies";

    private static final int NO_DATA = 0;
    private static final int INVALID_NO_CONNECTION = -4;
    private static final int INVALID_IO = -5;

    int nCountList = 0;
    boolean isFirstRefresh = true;
    MovieAdapter movieAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    CoordinatorLayout mainLayout;
    ImdbTopMoviesService moviesService;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://code2learn.me")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        moviesService = retrofit.create(ImdbTopMoviesService.class);

        if (BuildConfig.DEBUG) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                            | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                            | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                            | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            );
        }

        if (savedInstanceState == null) {
            fetchMovieData();
        }
    }

    void fetchMovieData() {
        MovieFetchJson movieFetchJson = new MovieFetchJson();
        movieFetchJson.execute();
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

    class MovieFetchJson extends AsyncTask<Void, Void, Integer> {

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

        private List<Movie> getMovies() throws IOException {
            Call<List<Movie>> moviesCall = moviesService.getMovies(nCountList);
            Response<List<Movie>> moviesResponse = moviesCall.execute();
            return moviesResponse.body();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                List<Movie> movies = getMovies();
                if (movies.isEmpty())
                    return NO_DATA;

                movieAdapter.clear();
                movieAdapter.addMovies(movies);
                nCountList += 20;

                return movies.size();
            } catch (UnknownHostException e) {
                return INVALID_NO_CONNECTION;
            } catch (IOException e) {
                return INVALID_IO;
            }
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
                case INVALID_IO:
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
