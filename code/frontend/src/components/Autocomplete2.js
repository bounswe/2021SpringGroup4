import React from "react";
import PlacesAutocomplete, {
    geocodeByAddress,
    getLatLng
} from "react-places-autocomplete";

//   <p>Latitude: {coordinates.lat}</p>
//<p>Longitude: {coordinates.lng}</p>
export default function Autocomplete2() {
    const [address, setAddress] = React.useState("");
    const [coordinates, setCoordinates] = React.useState({
    lat : null ,
    long : null
    });
  
    const handleSelect = async address => {
      const results = await geocodeByAddress(address);
      const latLng = await getLatLng(results[0]);
      setAddress(address);
      setCoordinates(latLng);
    };
  
    return (
       
      <div>
          <p>Latitude: {coordinates.lat}</p>
          <p>Longitude: {coordinates.lng}</p>
        
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
      </div>
    );
  }