// latlong.net

import React, { useState } from "react";
import {GoogleMap, withScriptjs, withGoogleMap, Marker,  InfoWindow } from  "react-google-maps"
import * as eventdata from "../data/eventdata.json";


function Map(){
    const [selectedPark, setSelectedPark] = useState(null);
    console.log(process.env.REACT_APP_GOOGLE_KEY); 

    return( 

    <GoogleMap 
    defaultZoom={10}
    defaultCenter = { { lat:41.008240, lng : 28.978359}}
    >


    {eventdata.features.map(park => (
        <Marker
          key={park.properties.PARK_ID}
          position={{
            lat: park.geometry.coordinates[0],
            lng: park.geometry.coordinates[1]
          }}
          onClick={() => {
            setSelectedPark(park);
          }}
         

        />
      ))} 


      {selectedPark && (
        <InfoWindow
          onCloseClick={() => {
            setSelectedPark(null);
          }}
          position={{
            lat: selectedPark.geometry.coordinates[0],
            lng: selectedPark.geometry.coordinates[1]
          }}
        >
          <div>
            <h2>{selectedPark.properties.NAME}</h2>
            <p>{selectedPark.properties.DESCRIPTIO}</p>
          </div>
        </InfoWindow>
        )
        
        
        }
      </GoogleMap>

    );

}

const MapWrapped = withScriptjs(withGoogleMap(Map));

export default function Maps() {
    
    
    return (
        <div style={{ width: "80vw", height: "80vh" }}  >
            <MapWrapped

               googleMapURL={`https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=geometry,drawing,places&key=${process.env.REACT_APP_GOOGLE_KEY}`}
            
               
              // googleMapURL={`https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=geometry,drawing,places&key=` }

              loadingElement={<div style={{ height: `100%` }} />}
              containerElement={<div style={{ height: `100%` }} />}
              mapElement={<div style={{ height: `100%` }} />}
             
             />
        </div>
    )
}

