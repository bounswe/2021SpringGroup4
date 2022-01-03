import './EventList.css';
import React, { useState } from 'react'
import Modal from './Modal'
import { Link, useHistory } from 'react-router-dom';
import Autocomplete2 from './Autocomplete2';
import  Maps from './Maps';


const EventList = ({events, title}) => {
console.log(events)
const [selectedEvent, setSelectedEvent] = useState(null)
const [selectedMap, setSelectedMap] = useState(null)
const history = useHistory()


const onFindEvent = ()=> {
    if(!!selectedEvent){
        const params = new URLSearchParams()
        params.append('eventId', selectedEvent.id)
        history.push('/eventDetail?' + params)
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
                        <Maps></Maps>  
                    </Modal>
                    }
            </div>
            <div className = "event-grid">{events.map((event) => (
                <div className="event-preview" key={event.id} onClick= {()=> setSelectedEvent(event)}>
                    <h2>{ event.body.title }</h2>
                    <p> Date: { event.body.date }</p>
                    <p> Location: { event.body.location }</p>
                    <p> Sport Type: { event.body.sportType }</p>
                    <button onClick ={onFindEvent} className = "btn btn-dark">Details</button>
                </div>
            ))}
            </div>
            {selectedEvent && <Modal isOpen={!!selectedEvent} onClose={() => setSelectedEvent(null)}> 
                <h2>{ selectedEvent.body.title }</h2>
                <p> { selectedEvent.body.description }</p>
                <p> Date: { selectedEvent.body.date }</p>
                <p> Time: { selectedEvent.body.time }</p>
                <p> Duration: { selectedEvent.body.duration }</p>
                <p> Location: { selectedEvent.body.location }</p>
                <p> Sport Type: { selectedEvent.body.sportType }</p>
                <p> Max Players: { selectedEvent.body.maxPlayers }</p>
                <p> Skill Level: { selectedEvent.body.skill_level }</p>
                <button onClick ={onFindEvent} className = "btn btn-dark">Details</button>   
            </Modal>
            }
        </div>
        

    );
}
export default EventList;
