var app = require('../server').app,
	request = require('supertest-as-promised')(app),
	assert = require('assert');

describe('IMDB Top 250 Movies', function () {
	it ('should let user query film data from server', function () {
		var offset = Math.floor((Math.random() * 10)) % 13;
		return request
			.get('/imdb_top_250?offset=' + offset)
			.then(function (res) {
				assert.ok(res.body.length == 20, 'Number: ' + res.body.length);
			});
	});
	it ('should let user download Android app', function () {
		return request
			.get('/download/imdb_app')
			.expect('Content-Type',
				'application/vnd.android.package-archive');
	});
});