import React, {useState, useEffect, useContext} from 'react'
import AuthContext from '../context/AuthContext'
import { useHistory } from 'react-router-dom'

import {GoogleMap, withScriptjs, withGoogleMap, Marker,  InfoWindow } from  "react-google-maps"
import PlacesAutocomplete, {
    geocodeByAddress,
    getLatLng
} from "react-places-autocomplete";
  


var latitude="";
var longitude="";
var userAdress="";
var fullAdress="";

function Map(){

    
    const [lng,setlng] =useState();
    const[lat,setlat]=useState();
    const [address, setAddress] = useState("");
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
   

        

    return( 

        <GoogleMap

            onClick={ev => {
                console.log("latitide = ", ev.latLng.lat());
                console.log("longitude = ", ev.latLng.lng());
                console.log(ev);
                
                setlng(ev.latLng.lng()  );
                setlat(ev.latLng.lat()  );

                latitude = ev.latLng.lat();
                longitude = ev.latLng.lng();
                reverseGeocodeCoordinates();
            }}
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
                    <Marker

                    position={{
                        lat: lat,
                        lng: lng
                    }}
                    onClick={() => {
                      

                    
                    }}


                
                    >

                        
                        <InfoWindow>
                            <h4>{userAdress} </h4>
                        </InfoWindow>

                       

                    </Marker>
            


        </GoogleMap>
     
    );

}    



function reverseGeocodeCoordinates(){
    
    fetch(`https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&sensor=false&key=AIzaSyANMJr1NuQJaRbCQXasdAE1DH1Wzbz005o`)
    .then(response => response.json() )
    .then(data=>  userAdress=data.results[4].formatted_address
        
    )
  


    .then(data => console.log(data))
    .catch(error => alert(error))

    console.log("User adress: " + userAdress) ;

    return(
        userAdress
        
    )
}

const MapWrapped = withScriptjs(withGoogleMap(Map));




const EventCreator = () => {   




    const history = useHistory()
    let {authTokens, logoutUser} = useContext(AuthContext)
    
    let createEvent = async (e) => {
        e.preventDefault()
        console.log(`
        --SUBMITTING--
        Title: ${e.target.title.value}
        Description: ${e.target.title.value}
        Time: ${e.target.time.value}
        Duration: ${e.target.duration.value}
        Authtoken : ${authTokens.access}
        Latitude : ${latitude}
        Longitude : ${longitude}
        Useraddr : ${userAdress}
      `);

        let response = await fetch('http://3.67.188.187:8000/api/events/', {
            method:'POST',
            headers:{
                'Content-Type':'application/json',
                'Authorization':'Bearer ' + String(authTokens.access)
            },
            body:JSON.stringify({'title':e.target.title.value, 'description':e.target.description.value, 'date':e.target.date.value , 'time': e.target.time.value , 
            'duration': e.target.duration.value , 'location': userAdress ,'sportType': e.target.sportType.value, 'maxPlayers': e.target.maxPlayers.value , 'skill_level': e.target.skill_level.value,  'lat' : latitude, 'long': longitude  })
        })
        
        let data = await response.json()
        
        console.log('Response Status' + response.status );

        if(response.status === 201){
            console.log('Successfully submitted event data' );
            history.push('/eventsuccess')
        }

        else if(response.status==500){
            console.log("Data is too long, select another place");
            alert("Data is too long, please select another place");
        }
        
        else{
            alert('Please check your credentials..Be careful')
        }
        
    }



    return (
            <div>
                <h1>Create New Event</h1>

                <form onSubmit={createEvent}>
            
            
            <table className="table table-bordered">
                <tbody>
                    <tr>
                        <th>Title</th>
                        <td>
                            <input type="text" name="title" className="form-control" placeholder="Title" />
                        </td>
                    </tr>
                    <tr>
                        <th>Description</th>
                        <td>
                            <input type="text" name="description" className="form-control" placeholder="Event Description" />
                        
                        </td>
                    </tr>

                    <tr>
                        <th>Date</th>
                        <td>
                            <input type="date" name="date" className="form-control" placeholder="Date" />
                        
                        </td>
                    </tr>

                    <tr>
                        <th>Time</th>
                        <td>
                            <input type="time" name="time" className="form-control" placeholder="Time" />
                        
                        </td>
                    </tr>

                    <tr>
                        <th>Duration</th>
                        <td>
                            <input type="time" name="duration" className="form-control" placeholder="Duration" />
                        
                        </td>
                    </tr>


                    <tr>
                        <th>Sport Type</th>
                        <td>
                            <input type="text" name="sportType" className="form-control" placeholder="Sport Type" />
                        
                        </td>
                    </tr>

                    <tr>
                        <th>Maximum Players</th>
                        <td>
                            <input type="text" name="maxPlayers" className="form-control" placeholder="Maximum Players" />
                        
                        </td>
                    </tr>

                    <tr>
                        <th> <label for="skill_level">Choose a skill level:</label>  </th>
                        <td>
                            <select  name="skill_level" id="skill_level">
                            
                                <option value="Beginner">Beginner</option>
                                <option value="Intermediate">Intermediate</option>
                                <option value="Advanced">Advanced</option>
                            </select>
                        </td>
                    </tr>

                    <div style={{ width: "40vw", height: "40vh" }}  >
                        <MapWrapped

                        googleMapURL={`https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=geometry,drawing,places&key=AIzaSyANMJr1NuQJaRbCQXasdAE1DH1Wzbz005o`}
                        
                        

                        loadingElement={<div style={{ height: `100%` }} />}
                        containerElement={<div style={{ height: `100%` }} />}
                        mapElement={<div style={{ height: `100%` }} />}
                        
                        />
                </div>
                
                    

                    <tr>
                        <td colSpan="2">
                            <input type="submit" value="Create" name="create" className="btn btn-dark"/>
                            
                        </td>
                    </tr>
                </tbody>
            </table> 

            </form>
                
        </div>
        )
    
}

export default EventCreator

