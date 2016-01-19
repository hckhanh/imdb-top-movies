var express = require('express');
var app = express();
var fs = require('fs');
var https = require('https');
var movies = require('./movies.js');

var sslKey = fs.readFileSync('/etc/nginx/ssl/privkey.pem');
var sslCert = fs.readFileSync('/etc/nginx/ssl/fullchain.pem');

var options = {
    key: sslKey,
    cert: sslCert
};

https.createServer(options, app)
	.listen(6789);

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
			res.status(404).end('This file is not available now!');
	});
});

app.all('*', function (req, res) {
	res.status(404).send('Invalid request!');
});

exports.app = app;