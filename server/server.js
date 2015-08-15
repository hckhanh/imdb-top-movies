var http = require('http');
var url = require('url');
var movies = require('./movies.js');
var mobile = require('./mobile.js');

http.createServer(function (request, response) {
	if (request.method != 'GET') {
		response.writeHead(404);
		response.end('Please send me a GET request!');
	}

	var queryData = url.parse(request.url, true);
	
	switch (queryData.pathname) {
		case '/imdb_top_250': // imdb_top_250?offset=0
			var data = movies.getData(Number(queryData.query.offset));
			
			response.writeHead(200, { 'Content-Type' : 'application/json' });
			response.write(JSON.stringify(data));
			response.end();
		break;
		case '/download/imdb_app': // Download Mobile app (Android)
			var appName = 'imdb-top-movies.apk';
			mobile.downloadApp('./build/' + appName, function (err, readStream, size) {
				if (err)
					return response.end('Sorry! This file is not currently available now!');

				response.writeHead(200, {
					'content-type' : 'application/vnd.android.package-archive',
					'content-length' : size,
					'content-disposition' : 'attachment; filename=' + appName
				});
				readStream.pipe(response);
			});
			break;
		default:
			response.writeHead(404);
			response.end('Invalid GET request!');
	}
}).listen(6789);