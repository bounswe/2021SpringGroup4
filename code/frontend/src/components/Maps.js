import React, { useState, useEffect } from "react";
import {GoogleMap, withScriptjs, withGoogleMap, Marker,  InfoWindow } from  "react-google-maps"
import * as eventdata from "../data/eventdata.json";
import PlacesAutocomplete, {
  geocodeByAddress,
  getLatLng
} from "react-places-autocomplete";


function Map(){


const [address, setAddress] = useState("");
const [selectedEvent, setSelectedEvent] = useState(null);
const [coordinates, setCoordinates] = useState({ lat:41.008240 , lng: 28.978359 });
const [zoom, setZoom] = useState(10);


const handleSelect = async event => {
  const newZoom = 16
  const results = await geocodeByAddress(event);
  const latLng = await getLatLng(results[0]);
  setAddress(event);
  setCoordinates(latLng);
  setZoom( newZoom );
  geocodeByAddress(event);
  console.log(address)
  //set cursor
  window.google.maps.Map(document.getElementsByClassName("GoogleMap")).panTo(latLng);
}; 

const reverseGeocodeCoordinates = async latLng => {
  fetch(`https://maps.googleapis.com/maps/api/geocode/json?latlng=${latLng.lat},${latLng.lng}&sensor=false&key=AIzaSyANMJr1NuQJaRbCQXasdAE1DH1Wzbz005o`)
  .then(response => response.json() )
  .then(data=> this.setState({
      userAdress:data.results[0].formatted_address
  })    )
  .then(data => console.log(data))
  .catch(error => alert(error))
  setAddress(address)
}



    const [clickedLatLng, setClickedLatLng] = useState(null);
    const [events, setEvents] = useState([]);
    const [isPending, setIsPending] = useState(true);
    const [error, setError] = useState(null);
    

    useEffect(() => {
      fetch('http://3.67.188.187:8000/api/events/', {
          method: 'GET'
      })
          .then(res => {
              console.log(res);
              if(!res.ok){
                  console.log(res);
                  throw Error('could not fetch the data for that resource');
              }
              console.log(res);
              return res.json();
          })
          .then(data => {
              setEvents(data.map(d => d.body));
              setIsPending(false);
              setError(null);
          })
          .catch(err => {
              setIsPending(false);
              setError(err.message);
          })
    }, [])

    return( 

      <GoogleMap 
        center= { { lat: coordinates.lat, lng : coordinates.lng } }
        zoom = { zoom }
      >
        <PlacesAutocomplete
         value={address}
         onChange={setAddress}
         onSelect={handleSelect}
       >
         {({ getInputProps, suggestions, getSuggestionItemProps, loading }) => (
           <div>
 
             <input {...getInputProps({ placeholder: "Enter Location" })} />
 
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

        {events.map((event, index) => {
          const feature = {
            type: "Feature",
          }
          return <Marker
            key={index}
            position={{
              lat: event.lat,
              lng: event.long
            }}
            onClick={() => {
              setSelectedEvent(event);
              
            //window.google.maps.Map(document.getElementsByClassName("GoogleMap")).panTo(event);
            }}
          />
        })} 

        {selectedEvent && (
          <InfoWindow
            onCloseClick={() => {
              setSelectedEvent(null);
            }}
            position={{
              lat: selectedEvent.lat,
              lng: selectedEvent.long
            }}
          >
            <div>
            <h2>{ selectedEvent.title }</h2>
            <p> { selectedEvent.description }</p>
            <p> Date: { selectedEvent.date }</p>
            <p> Sport Type: { selectedEvent.sportType }</p>
            <p> Max Players: { selectedEvent.maxPlayers }</p>
            <p> Skill Level: { selectedEvent.skill_level }</p>
            <div className = "button-block-modal">
                <a href ="./login" className = "btn btn-dark btn-lg">Join!</a>
                  </div>    
            </div>
          </InfoWindow>
        )}
      </GoogleMap>
    );

}

const MapWrapped = withScriptjs(withGoogleMap(Map));

export default function Maps() {
    
    return (
        <div style={{ width: "60vw", height: "60vh" }}  >
            <MapWrapped
               googleMapURL={`https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=geometry,drawing,places&key=AIzaSyANMJr1NuQJaRbCQXasdAE1DH1Wzbz005o`}
               loadingElement={<div style={{ height: `100%` }} />}
               containerElement={<div style={{ height: `100%` }} />}
               mapElement={<div style={{ height: `100%` }} />}
             
             />
        </div>
    )
}

