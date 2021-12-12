



import React, { useState } from "react";
import {GoogleMap, withScriptjs, withGoogleMap, Marker,  InfoWindow } from  "react-google-maps"
import * as eventdata from "../data/eventdata.json";
import PlacesAutocomplete, {
  geocodeByAddress,
  getLatLng
} from "react-places-autocomplete";





function Map(){





const [address, setAddress] = React.useState("");
const [selectedPark, setSelectedPark] = useState(null);
const [coordinates, setCoordinates] = React.useState({
lat : '',
long : ''
});

const handleChange = address => {
  this.setState({ address });
};




const handleSelect = async park => {
  const results = await geocodeByAddress(park);
  const latLng = await getLatLng(results[0]);
  setAddress(park);
  setCoordinates(latLng);
  geocodeByAddress(park);
  //set cursor
  window.google.maps.Map(document.getElementsByClassName("GoogleMap")).panTo(latLng);
  
}; 



const [clickedLatLng, setClickedLatLng] = useState(null);


    

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
            window.google.maps.Map(document.getElementsByClassName("GoogleMap")).panTo(park);
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
    <PlacesAutocomplete
         
         value={address}
         onChange={setAddress}
         onSelect={handleSelect}
       >
         {({ getInputProps, suggestions, getSuggestionItemProps, loading }) => (
           <div>
          
 
             <input {...getInputProps({ placeholder: "Location" })} />
 
             <div>
               {loading ? <div>...loading</div> : null}
 
               {suggestions.map(suggestion => {
                 const style = {
                   backgroundColor: suggestion.active ? "#e79686" : "#a39391"
                 };
 
                 return (
                   <div {...getSuggestionItemProps(suggestion, { style })}>
                     {suggestion.description}
                   </div>
                 );
               })}
             </div>
           </div>
         )}
       </PlacesAutocomplete>
    
      </GoogleMap>

    );

}

const MapWrapped = withScriptjs(withGoogleMap(Map));

export default function Maps() {
    
    
    return (
        <div style={{ width: "60vw", height: "60vh" }}  >
            <MapWrapped

               googleMapURL={`https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=geometry,drawing,places&key=`}
               loadingElement={<div style={{ height: `100%` }} />}
               containerElement={<div style={{ height: `100%` }} />}
               mapElement={<div style={{ height: `100%` }} />}
             
             />
        </div>
    )
}

