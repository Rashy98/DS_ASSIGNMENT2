import React,{Component} from 'react';
import './App.css';
import axios from 'axios';
import {Row,Col, Container, Jumbotron} from "react-bootstrap";

class App extends Component{
constructor(props) {
    super(props);

    this.state = {
        sensors: [],
        interval : null,

    }
}

    getSensorData() {

            axios.get(`http://localhost:8000/api/sensors`, {
                headers: {
                    'Content-Type': 'application/json; charset=utf-8'
                }
            })
                .then(res => {
                    const data = res.data;
                    const sensors = data.data;

                    this.setState({
                        sensors
                    })

                })
                .catch((error) => {
                    console.log(error)
                })

    }

componentDidMount() {


    this.getSensorData();
    setInterval(this.getSensorData,40000);
    // this.interval = setInterval(() => this.setState({ time: Date.now() }), 1000);
}

componentWillUnmount() {
    clearInterval(this.interval);
}

    SmokeLevelAndCo2LevelExceeds(){
    this.state.sensors.map(sensor =>{
        console.log(sensor.smokeLevel);
        if(sensor.smokeLevel >= 5 && sensor.Co2Level >=5) {
            return true;
            console.log('lol')

        }
        else {
            return false;
            console.log('aawe naaa');
        }
    })
}

    render() {

            return (

                <div className="App" style={{backgroundColor:"#465358",height:"100%",maxWidth:"100%" }}>
                    <meta http-equiv="refresh" content="40"/>
                    <Jumbotron fluid style ={{

                        alignItems: "center",
                        justifyContent: "center",
                        backgroundColor: 'black',
                        flex: 1}} className = "col-50 mx-auto col-mid-30 col-sm-20" >
                        <Container>
                            <center>
                                <h1 style={{color:'#f9eeeb'}}>Sensors List</h1>

                            </center>
                        </Container>
                    </Jumbotron>
                    <div>
                        <ul style={{display:"inline"}}>
                            <li style={{color:' #c9ffcb',display:"inline",margin:"5rem"}}>*Active and Everything is under control  </li>
                            <li style={{color:' #ff5148',display:"inline",margin:"5rem"}}> *Active but either smoke level or Co2 Level is higher  </li>
                            <li style={{color:' grey',display:"inline",margin:"5rem"}}>*Sensor is inactive  </li>
                        </ul>
                        <br/>
                    </div>
                    <div style={{overflow:"auto",whiteSpace:"nowrap"}}>
                        <Row>
                                {this.state.sensors.map(sensor =>{
                                    if((sensor.smokeLevel>= 5 || sensor.Co2Level >= 5) && sensor.status == "Active" ){

                                        return( <div className='Container text-dark' style={{backgroundColor:'#ff5148',borderRadius:'10px',alignItems:"left",height: "18rem",width:"15rem",display:"inline-block",margin:"5rem"}} >
                                                <h3>Sensor ID: {sensor.Id}</h3>
                                                <h3>Status: {sensor.status}</h3>
                                                <h4>Floor Number: {sensor.floorNo}</h4>
                                                <h4>Room Number: {sensor.roomNo}</h4>
                                                <h4>Smoke Level: {sensor.smokeLevel}</h4>
                                                <h4>Co2 Level : {sensor.Co2Level}</h4>

                                            </div>
                                        )
                                    }
                                    else if(sensor.status == "Active" ){
                                        return( <div className='Container text-dark' style={{backgroundColor:' #c9ffcb',borderRadius:'10px',alignItems:"left",height: "18rem",width:"15rem",display:"inline-block",margin:"5rem"}} >
                                                <h3>Sensor ID: {sensor.Id}</h3>
                                                <h3>Status: {sensor.status}</h3>
                                                <h4>Floor Number: {sensor.floorNo}</h4>
                                                <h4>Room Number: {sensor.roomNo}</h4>
                                                <h4>Smoke Level: {sensor.smokeLevel}</h4>
                                                <h4>Co2 Level : {sensor.Co2Level}</h4>

                                            </div>
                                        )
                                    }
                                    else{
                                        return( <div className='Container text-dark' style={{backgroundColor:'grey',borderRadius:'10px',alignItems:"left",height: "18rem",width:"15rem",display:"inline-block",margin:"5rem"}} >
                                                <h3>Sensor ID: {sensor.Id}</h3>
                                                <h3>Status: {sensor.status}</h3>
                                                <h4>Floor Number: {sensor.floorNo}</h4>
                                                <h4>Room Number: {sensor.roomNo}</h4>
                                                <h4>Smoke Level: {sensor.smokeLevel}</h4>
                                                <h4>Co2 Level : {sensor.Co2Level}</h4>


                                            </div>
                                        )
                                    }
                                }
                                )}

                        </Row>

                    </div>
                            </div>






            );
        }


}

export default App;
