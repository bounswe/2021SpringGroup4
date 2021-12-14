import './EventList.css';
import React, { useState } from 'react'
import Modal from './Modal'
import { Link, useHistory } from 'react-router-dom';
import Autocomplete2 from './Autocomplete2';
import  Maps from './Maps';





const EventList = ({events, title}) => {
const [selectedEvent, setSelectedEvent] = useState(null)
const [selectedMap, setSelectedMap] = useState(null)
const [searchInputs, setSearchInputs ] = useState({location:'', dist:''})
const history = useHistory()


const onFindEvent = ()=> {
    if(searchInputs.location && searchInputs.dist){
        const params = new URLSearchParams()
        params.append('location', searchInputs.location)
        params.append('dist', searchInputs.dist)
        history.push('/searchEvents?' + params)
    }
}

    return (  
        <div className="event-list">
            <h2 style={{marginTop:20}}>{ title }</h2>
            <div className = "map-view"> 
                <div className="map" onClick= {()=> setSelectedMap(Maps)}>
                    <button className = "btn btn-dark">Map View!</button>
                    </div>
                    {selectedMap && <Modal isOpen={!!selectedMap} onClose={() => setSelectedMap(null)}> 
                        <Maps events={events}></Maps>  
                    </Modal>
                    }-
                </div>
            <div className = "event-grid">{events.map((event) => (
                <div className="event-preview" key={event.id} onClick= {()=> setSelectedEvent(event)}>
                    <h2>{ event.title }</h2>
                    <p> Date: { event.date }</p>
                    <p> Location: { event.location }</p>
                    <p> Sport Type: { event.sportType }</p>
                    <div className = "button-block">
                        
                        <Link to ="./login" className = "btn btn-dark">Join!</Link>
                    </div>  
                </div>
            ))}</div>
            {selectedEvent && <Modal isOpen={!!selectedEvent} onClose={() => setSelectedEvent(null)}> 
                <h2>{ selectedEvent.title }</h2>
                <p> { selectedEvent.description }</p>
                <p> Date: { selectedEvent.date }</p>
                <p> Time: { selectedEvent.time }</p>
                <p> Duration: { selectedEvent.duration }</p>
                <p> Location: { selectedEvent.location }</p>
                <p> Sport Type: { selectedEvent.sportType }</p>
                <p> Max Players: { selectedEvent.maxPlayers }</p>
                <p> Skill Level: { selectedEvent.skill_level }</p>
                <div className = "button-block-modal">
                    <Link to ="./login" className = "btn btn-dark btn-lg">Join!</Link>
                </div>    
            </Modal>
            }
        </div>
        

    );
}
export default EventList;