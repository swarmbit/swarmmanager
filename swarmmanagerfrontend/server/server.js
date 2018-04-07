var express = require('express'),
    path = require('path'),
    fs = require('fs'),
    proxy = require('express-http-proxy'),
    http = require('http'),
    https = require('https');

var app = express();
var staticRoot = __dirname + '/static';

var isHttps = process.env.HTTPS;

var port = process.env.PORT || 3080;
var httpsPort = process.env.HTTPSPORT || 3443;

app.use("/api/*", proxy('localhost:8080', {
    proxyReqPathResolver: function (req, res) {
        return require('url').parse(req.baseUrl).path;
    }
}));
app.use(express.static(staticRoot));
app.use(function(req, res, next){
    var accept = req.accepts('html', 'json', 'xml');
    if(accept !== 'html'){
        return next();
    }
    var ext = path.extname(req.path);
    if (ext !== ''){
        return next();
    }
    fs.createReadStream(staticRoot + '/index.html').pipe(res);
});

if (isHttps !== "true") {
  var httpServer = http.createServer(app);
  httpServer.listen(port, function() {
    console.log('Running http on port', port);
  });
} else {
  var privateKey  = fs.readFileSync('/config/server.key', 'utf8');
  var certificate = fs.readFileSync('/config/server.crt', 'utf8');
  var credentials = {key: privateKey, cert: certificate};
  var httpsServer = https.createServer(credentials, app);
  httpsServer.listen(httpsPort, function() {
    console.log('Running https on port', httpsPort);
  });
  var httpServer = http.createServer(function (req, res) {
	    res.writeHead(301, { "Location": "https://" + req.headers['host'] + req.url });
	    res.end();
	})
  httpServer.listen(port, function() {
    console.log('Running http on port with https redirect', port);
  });
}
