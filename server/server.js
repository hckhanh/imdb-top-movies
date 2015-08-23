var express = require('express');
var app = express();
var movies = require('./movies.js');

app.get('/imdb_top_250', function (req, res) {
	if (req.query.offset)
		res.json(movies.getData(Number(req.query.offset)));
	else
		res.status(404).send('Invalid request!');
});

app.get('/download/imdb_app', function (req, res) {
	var appName = 'imdb-top-movies.apk';
	res.download('./build/' + appName, appName, function (err) {
		if (err && err.code == 'ENOENT')
			res.status(404).end('Sorry! This file is not currently available now!');
	});
});

app.listen(6789);