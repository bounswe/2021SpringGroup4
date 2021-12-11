// https://www.w3schools.com/html/html5_geolocation.asp
//  https://developers.google.com/maps/documentation/maps-static/dev-guide

// https://developers.google.com/maps/documentation/geocoding/overview

// USES GOOGLE MAPS STATIC API
// USES GOOGLE MAP REVERSE GEOCODE
//import { configure } from '@testing-library/dom';
import React, { Component } from 'react'
<script src="https://maps.googleapis.com/maps/api/js?key=&libraries=places"></script>

class UserGeolocation extends Component {

    constructor(props) {
        super(props);
        this.state = {
            latitude : null,
            longitude: null,
            userAdress: null
        };

        this.getLocation = this.getLocation.bind(this);
        this.getCoordinates=this.getCoordinates.bind(this);
        this.reverseGeocodeCoordinates=this.reverseGeocodeCoordinates.bind(this);
    }


    getLocation() {
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(this.getCoordinates, this.handleLocationError );
        } else {
          alert("Geolocation is not supported by this browser." ) ;
        }
      }

    getCoordinates(position){
        console.log(position)
        this.setState({
            latitude: position.coords.latitude,
            longitude:position.coords.longitude
        })
        this.reverseGeocodeCoordinates();
    }

    reverseGeocodeCoordinates(){
        fetch(`https://maps.googleapis.com/maps/api/geocode/json?latlng=${this.state.latitude},${this.state.longitude}&sensor=false&key=`)
        .then(response => response.json() )
        .then(data=> this.setState({
            userAdress:data.results[0].formatted_address
        })    )


        .then(data => console.log(data))
        .catch(error => alert(error))
    }


    handleLocationError(error) {
        switch(error.code) {
          case error.PERMISSION_DENIED:
            alert( "User denied the request for Geolocation." )
            break;
          case error.POSITION_UNAVAILABLE:
            alert ( "Location information is unavailable." )
            break;
          case error.TIMEOUT:
            alert ( "The request to get user location timed out." )
            break;
          case error.UNKNOWN_ERROR:
            alert ( "An unknown error occurred." )
            break;
        }
      }



    render() {
        return (
            <div>
                <h2> User Geolocation </h2>
                <button onClick={this.getLocation} >   Get Coordinates </button>
                <h4>Coordinates </h4>
                <p>Latitude: {this.state.latitude} </p>
                <p>Latitude: {this.state.longitude} </p>
                
                
                <h4>Google Maps Reverse Geocoding</h4>
                <p> Adress : { this.state.userAdress} </p>

                {
                    this.state.latitude && this.state.longitude ?
              
              <img src= {`https://maps.googleapis.com/maps/api/staticmap?center=${this.state.latitude},${this.state.longitude}&zoom=14&size=400x400&sensor=false&markers=color:red%7C${this.state.latitude},${this.state.longitude}&key=`} alt='' />
                    :
                    null

                }



            </div>
        )
    }
}

export default UserGeolocation
