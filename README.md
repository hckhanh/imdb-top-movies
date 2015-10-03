# imdb-top-movies [![Build Status](https://travis-ci.org/hckhanh/imdb-top-movies.svg)](https://travis-ci.org/hckhanh/imdb-top-movies) [![Dependency Status](https://gemnasium.com/hckhanh/imdb-top-movies.svg)](https://gemnasium.com/hckhanh/imdb-top-movies)
This is an DEMO IMDB Top Movies app include server and client sides.

--------

This application is created by following this tutorial:
	
http://www.androidhive.info/2015/05/android-swipe-down-to-refresh-listview-tutorial/

When I do this tutorial. I just think about how to implement Swipe Refresh Layout in Android. Because I am studying about **Android Material Design**. It's very awesome!
But I think I can make an full-side application. So I try learning Node.js and try to replace the current API by **my** own API :D.

This is old API:

	http://api.androidhive.info/json/imdb_top_250.php?offset=[offset-number]

And, this is my **new** API:
	
	http://code2learn.me/imdb_top_250?offset=[offset-number]
	
**Note:**

The `offset-number` is the range from **0** to **250**

I make an Android app to receive information from the **Node.js** server. You can download it here:

http://code2learn.me/download/imdb_app

## Performance

When I try to implement PHP API, It takes me about **2.5s** to load 20 movies data from the server (include movie rank and movie name). On the other hand, Node.js server only takes about **0.3s** even less to send these data.

The server which is running on Node.js is faster than PHP server **8x times**. It's amazing!

## Continuous Integration (Travis CI)

APK file (Android) is tested automatically by Travis CI host.
