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
                    <h2>{ event.body.title }</h2>
                    <p> { event.body.description }</p>
                    <p> Date: { event.body.date }</p>
                    <p> Location: { event.body.location }</p>
                    <p> Sport Type: { event.body.sportType }</p>
                    <div className = "button-block">
                        <Link to ="./login" className = "btn btn-dark">Join!</Link>
                    </div>  
                </div>
            ))}</div>
            {selectedEvent && <Modal isOpen={!!selectedEvent} onClose={() => setSelectedEvent(null)}> 
                <h2>{ selectedEvent.body.title }</h2>
                <p> { selectedEvent.body.description }</p>
                <p> Date: { selectedEvent.body.date }</p>
                <p> Time: { selectedEvent.body.time }</p>
                <p> Duration: { selectedEvent.body.duration }</p>
                <p> Location: { selectedEvent.body.location }</p>
                <p> Sport Type: { selectedEvent.body.sportType }</p>
                <p> Max Players: { selectedEvent.body.maxPlayers }</p>
                <p> Applicants: { selectedEvent.body.applicants }</p>
                <p> Participants: { selectedEvent.body.participants }</p>
                <p> Followers: { selectedEvent.body.followers }</p>
                <div className = "button-block-modal">
                    <Link to ="./login" className = "btn btn-dark btn-lg">Join!</Link>
                </div>    
            </Modal>}
             
        </div>

    );
}
export default EventList;
