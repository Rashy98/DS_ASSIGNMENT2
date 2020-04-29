'use strict'

var mysql = require('mysql');

var dbConn = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'ds_api'
});
// connect to database
dbConn.connect();

module.exports = dbConn;