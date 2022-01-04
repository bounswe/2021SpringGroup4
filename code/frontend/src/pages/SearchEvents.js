import { useState, useEffect, useContext } from 'react';
import EventList from '../components/EventList';
import { useLocation } from 'react-router-dom'
import AuthContext from '../context/AuthContext';



const Events = () => {
    const [events, setEvents] = useState(null);
    const [isPending, setIsPending] = useState(true);
    const [error, setError] = useState(null);
    const location = useLocation();
    const params = new URLSearchParams(location.search);
    let {myusername, user} = useContext(AuthContext);

 
    useEffect(() => {
        fetch('http://3.67.188.187:8000/api/search/event/applicant/', {
            method: 'POST',
            body: JSON.stringify({
                'username': myusername
            }),
            headers:{
                'Content-type': 'application/json; charset=UTF-8',
            },
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
                setEvents(data);
                setIsPending(false);
                setError(null);
            })
            .catch(err => {
                setIsPending(false);
                setError(err.message);
            })
    }, [])

    return ( 
        <div className="events">
            {  error && <div>{ error }</div>  }
            {  isPending && <div>Loading...</div> }
            {events && <EventList events={events} title = "Going"/>}
        </div>
     );
}
 
export default Events;