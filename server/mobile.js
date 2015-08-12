var fs = require('fs');

module.exports.downloadApp = downloadApp;

function downloadApp (file, callback) { // callback(err, buffer, size)
	fs.stat(file, function (err, stats) {
		if (err)
			return callback(err, null, -1);

		if (stats.isFile()) {
			var readStream = fs.createReadStream(file);
			return callback(null, readStream, stats.size);
		} else 
			return callback(new Error('This is not a file!'), null, -1);
	});
};