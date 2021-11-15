import './EventList.css';
import React, { useState } from 'react'
import Modal from './Modal'
import { Link } from 'react-router-dom';

const EventList = ({events, title}) => {
const [selectedEvent, setSelectedEvent] = useState(null)

    return (  
        <div className="event-list">
            <h2>{ title }</h2>
            <div className = "event-grid">{events.map((event) => (
                <div className="event-preview" key={event.id} onClick= {()=> setSelectedEvent(event)}>
                    <h2>{ event.title }</h2>
                    <p> { event.description }</p>
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
                <p> Applicants: { selectedEvent.applicants }</p>
                <p> Participants: { selectedEvent.participants }</p>
                <p> Followers: { selectedEvent.followers }</p>
                <div className = "button-block-modal">
                    <Link to ="./login" className = "btn btn-dark btn-lg">Join!</Link>
                </div>    
            </Modal>}
             
        </div>

    );
}
export default EventList;
