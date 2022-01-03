import './EventListDetail.css';
import React, { useState, useContext } from 'react';
import { Link, NavLink, useHistory } from 'react-router-dom';
import AuthContext from '../context/AuthContext';


const EventListDetail = ({events: eventsFromParent, title}) => {
const history = useHistory()
const [participants, setParticipants] = useState([
    'Tolga', 'Yigit', 'Test'
]);

const [events, setEvents] = useState(eventsFromParent)

const [selectedUser, setSelectedUser] = useState(null)
const [selectedSportType, setSelectedSportType] = useState(null)
const [selectedEvent, setSelectedEvent] = useState(null)
let {myusername, user} = useContext(AuthContext);
let {authTokens, logoutUser} = useContext(AuthContext)

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

const onApplyEvent = ()=> {
    if(!!selectedEvent){
        console.log(selectedEvent)
        console.log(authTokens)
        console.log(myusername)
        window.alert("Succesfully Applied!")
        fetch('http://3.67.188.187:8000/api/events/' + selectedEvent.id + '/', {
            method: 'PATCH',
            headers:{
                'Content-type': 'application/json; charset=UTF-8',
                'Authorization':'Bearer ' + String(authTokens.access)
            },
            body: JSON.stringify(
                {
                    "applicants": {
                        "add": [myusername]  
                    }     
                }
            )
        }).then(res => {
            console.log(res);
            if(!res.ok){
                console.log(res);
                throw Error('could not fetch the data for that resource');
            }
            console.log(res);
            return res.json();
        }).then(()=>{
            fetch('http://3.67.188.187:8000/api/events/' + selectedEvent.id + '/', {
                method: 'GET'
            }).then(res => {
                console.log(res);
                if(!res.ok){
                    console.log(res);
                    throw Error('could not fetch the data for that resource');
                }
                console.log(res);
                return res.json();
            })
            .then(data => {
                console.log(data)
                setEvents(data);
            })
        })



        //window.location.href = window.location.href

    }
}

    return (  
        <div className="event-list-detail">
            <div className = "event-grid-detail">{
                <div className="event-preview-detail" key={events.id}>
                    <h2>{ events.body.title }</h2>
                    <div className = "sport-type"> {
                            <button onClick={() => {
                                setSelectedSportType(events.body.sportType);
                                onFindSportType();
                            }} className = "btn btn-dark" >{events.body.sportType}</button>
                            }
                            <p>Click to see equipments of {events.body.sportType}</p>
                            </div>
                    <div className="event-features-detail">
                        <div className="event-inner-detail">
                            <p> Date: { events.body.date }</p>
                            <p> Location: { events.body.location }</p>
                            <p> Date: { events.body.date }</p>
                            <p> Time: { events.body.time }</p>
                            <p> Duration: { events.body.duration }</p>
                            <p> Max Players: { events.body.maxPlayers }</p>
                            <p> Skill Level: { events.body.skill_level }</p>
                        </div>
                        <div className = "participants"> Applicants:{ events.body.applicants.map((user) => (
                            <p style={{display:"block", cursor:"pointer"}} onClick={() => {
                                setSelectedUser(user.toLowerCase());
                                onFindUser();
                              }}> {user.toUpperCase()}</p>
                        ))
                        }
                        </div>
                    </div>
                    <div className = "button-block">
                        <button style={{display:"block"}} onClick={() => {
                                    setSelectedEvent(events);
                                    onApplyEvent();
                                }} className = "btn btn-dark"> Apply!</button>
                    </div>  
                </div>
            }
            </div>
        </div>
        

    );
}
export default EventListDetail;