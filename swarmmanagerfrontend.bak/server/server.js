var express = require('express'),
    path = require('path'),
    fs = require('fs'),
    proxy = require('express-http-proxy');

var app = express();  
var staticRoot = __dirname + '/static'; 
 
app.set('port', (process.env.PORT || 3000));
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
app.listen(app.get('port'), function() {  
    console.log('Running on port', app.get('port'));
});
