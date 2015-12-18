package com.demo.imdb.top.movies;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity_> {

    private Solo solo;

    public MainActivityTest() {
        super(MainActivity_.class);
    }

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testSwipeToRefresh() {
        // unlock the screen if it's locked
        solo.unlockScreen();
        // touch and drag the screen
        solo.drag(42, 42, 230, 630, 10);
        // Check the title of the film
        boolean isMatched = solo.searchText("City of God");
        assertTrue(isMatched);
    }

}