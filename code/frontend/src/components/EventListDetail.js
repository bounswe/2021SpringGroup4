import './EventListDetail.css';
import React, { useState } from 'react'
import { Link, useHistory } from 'react-router-dom';


const EventListDetail = ({events, title}) => {
const history = useHistory()
const [participants, setParticipants] = useState([
    'Tolga', 'Yigit', 'Test'
]);

const [selectedUser, setSelectedUser] = useState(null)
const [selectedSportType, setSelectedSportType] = useState(null)

const onFindUser = ()=> {
    if(!!selectedUser){
        const params = new URLSearchParams()
        params.append('userName', selectedUser)
        history.push('/userProfileSelect?' + params)
    }
}

const onFindSportType = ()=> {
    if(!!selectedSportType){
        const params = new URLSearchParams()
        params.append('sportType', selectedSportType)
        history.push('/equipment?' + params)
    }
}

    return (  
        <div className="event-list-detail">
            <div className = "event-grid-detail">{
                <div className="event-preview-detail" key={events.id}>
                    <h2>{ events.title }</h2>
                    <div className="event-features-detail">
                        <div className="event-inner-detail">
                            <p> Date: { events.date }</p>
                            <p> Location: { events.location }</p>
                            <p> Date: { events.date }</p>
                            <p> Time: { events.time }</p>
                            <p> Duration: { events.duration }</p>
                            <p> Location: { events.location }</p>
                            <p> Max Players: { events.maxPlayers }</p>
                            <p> Skill Level: { events.skill_level }</p>
                            <div className = "sport-type"> { 
                                <button onClick={() => {
                                    setSelectedSportType(events.sportType);
                                    onFindSportType();
                                }} className = "btn btn-dark" >Sport Type: {events.sportType}</button>
                            }
                            </div>
                        </div>
                        <div className = "participants"> { participants.map((user) => (
                            <button onClick={() => {
                                setSelectedUser(user.toLowerCase());
                                onFindUser();
                              }} className = "btn btn-dark" > {user}</button>
                        ))
                        }
                        </div>
                    </div>
                    <div className = "button-block">
                            <Link to ="./login" className = "btn btn-dark">Apply!</Link>
                    </div>  
                </div>
            }
            </div>
        </div>
        

    );
}
export default EventListDetail;
