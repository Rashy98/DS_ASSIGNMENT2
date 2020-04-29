var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mysql = require("mysql");

var dbConn = require('../models/database');

module.exports = function(app) {

    app.use(function(req, res, next) {
        res.header("Access-Control-Allow-Origin", "*");
        res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        next();
    });

    app.get('/api/sensors', function (req, res) {

        dbConn.query('SELECT * FROM Sensors', function (error, results, fields) {
            if (error) throw error;
            return res.send({error: false, data: results, message: 'Sensor List.'});
        });
    });

    app.post('/api/sensors', function (req, res) {
        let floorNo = req.body.floorNo;
        let roomNo = req.body.roomNo;
        let smokeLevel = req.body.smokeLevel;
        let Co2Level = req.body.Co2Level;
        let status = req.body.status;

        if (!floorNo || !roomNo || !smokeLevel || !Co2Level || !status) {
            return res.status(400).send({error: true, message: 'Please Enter sensor details'});
        }

        dbConn.query("INSERT INTO Sensors SET ?",{floorNo:floorNo, roomNo:roomNo,smokeLevel:smokeLevel,Co2Level:Co2Level,status:status},function (error, results, fields) {
            if (error) throw error;
            return res.send({
                error: false,
                data: results,
                message: 'New sensor has been added to the system successfully.'
            });
        });
    });

    app.put('/api/sensors', function (req, res) {
        let sensor_id = req.body.Id;
        let smokeLevel = req.body.smokeLevel;
        let Co2Level = req.body.Co2Level;
        // let status = req.body.status;

        if (!sensor_id||!smokeLevel || !Co2Level) {
            return res.status(400).send({ message: 'Please provide sensor details and sensor id' });
        }
        dbConn.query("UPDATE Sensors SET smokeLevel = ?,Co2Level = ?  WHERE id = ?", [smokeLevel,Co2Level, sensor_id], function (error, results, fields) {
            if (error) throw error;
            return res.send({ error: false, data: results, message: 'sensor has been updated successfully.' });
        });
    });

    app.put('/api/sensors/admin', function (req, res) {
        let sensor_id = req.body.Id;
        let floorNo = req.body.floorNo;
        let roomNo = req.body.roomNo;
        let status = req.body.status;

        if (!sensor_id || !floorNo || !roomNo || !status) {
            return res.status(400).send({ message: 'Please provide sensor details and sensor id' });
        }
        dbConn.query("UPDATE Sensors SET floorNo = ? ,roomNo = ? ,status =? WHERE id = ?", [floorNo,roomNo,status, sensor_id], function (error, results, fields) {
            if (error) throw error;
            return res.send({ error: false, data: results, message: 'sensor has been updated successfully.' });
        });
    });

    app.put('/api/sensors/desktopPut', function (req, res) {
        let sensor_id = req.body.Id;
        let roomNo = req.body.roomNo;
        let floorNo = req.body.floorNo;
        let status = req.body.status;

        if (!sensor_id||!roomNo || !floorNo) {
            return res.status(400).send({ message: 'Please provide sensor details and sensor id' });
        }
        dbConn.query("UPDATE Sensors SET roomNo = ?,floorNo = ? ,status =? WHERE id = ?", [roomNo,floorNo,status, sensor_id], function (error, results, fields) {
            if (error) throw error;
            return res.send({ error: false, data: results, message: 'sensor has been updated successfully.' });
        });
    });

    app.delete('/api/sensors', function (req, res) {
        let sensor_id = req.body.Id;

        if (!sensor_id) {
            return res.status(400).send({ error: true, message: 'Please select a sensor' });
        }
        dbConn.query('DELETE FROM Sensors WHERE Id = ?', [sensor_id], function (error, results, fields) {
            if (error) throw error;
            return res.send({ error: false, data: results, message: 'Sensor has been deleted successfully.' });
        });
    });

}

