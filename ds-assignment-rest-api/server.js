var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mysql = require("mysql");


app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));
// default route
app.get('/', function (req, res) {
    return res.send({ error: true, message: 'hello' })
});
// set port
app.listen(8000, function () {
    console.log('Node app is running on port 8000');
});
var routes = require('./app/routes/sensorRoutes');
routes(app);
module.exports = app;


