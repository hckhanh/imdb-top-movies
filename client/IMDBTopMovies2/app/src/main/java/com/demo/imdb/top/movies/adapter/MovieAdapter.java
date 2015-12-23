package com.demo.imdb.top.movies.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.imdb.top.movies.R;
import com.demo.imdb.top.movies.data.Movie;
import com.demo.imdb.top.movies.utils.DisplayMetricsHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends BaseAdapter {

    ArrayList<Movie> mMovies;
    LayoutInflater mLayoutInflater;
    String[] mRankColors;
    float density;

    /**
     * Constructor
     *
     * @param context    The context of the current screen (Activity)
     * @param rankColors Color of the rank (movie item)
     */
    public MovieAdapter(Context context, String[] rankColors) {
        this.mRankColors = rankColors;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DisplayMetricsHelper metricsHelper = new DisplayMetricsHelper(context);
        density = metricsHelper.getDensity();
        this.mMovies = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public MovieAdapter(Context context, String[] rankColors, Serializable movies) {
        this.mRankColors = rankColors;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DisplayMetricsHelper metricsHelper = new DisplayMetricsHelper(context);
        density = metricsHelper.getDensity();

        try {
            this.mMovies = (ArrayList<Movie>) movies;
        } catch (ClassCastException e) {
            this.mMovies = new ArrayList<>();
        }
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return mMovies.size();
    }

    public void addItem(Movie movie) {
        mMovies.add(movie);
    }

    public ArrayList<Movie> getListMovie() {
        return mMovies;
    }

    public void clear() {
        mMovies.clear();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return mMovies.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View movieItem;
        MovieItemHolder movieItemHolder;

        if (convertView != null) {
            movieItem = convertView;
            movieItemHolder = (MovieItemHolder) movieItem.getTag();
        } else {
            movieItem = mLayoutInflater.inflate(R.layout.movie_item, parent, false);
            movieItemHolder = new MovieItemHolder();
            movieItem.setTag(movieItemHolder);

            movieItemHolder.rankTextView = (TextView) movieItem.findViewById(R.id.movie_rank);
            GradientDrawable gradientDrawable = (GradientDrawable) movieItemHolder.rankTextView.getBackground();
            gradientDrawable.setStroke(15 * (int) density, Color.parseColor(mRankColors[position % mRankColors.length]));

            movieItemHolder.nameTextView = (TextView) movieItem.findViewById(R.id.movie_name);
        }

        movieItemHolder.rankTextView.setText(String.format("%d", mMovies.get(position).getRank()));
        movieItemHolder.nameTextView.setText(mMovies.get(position).getName());

        return movieItem;
    }

    public void addMovies(List<Movie> movies) {
        this.mMovies.addAll(movies);
    }
}
