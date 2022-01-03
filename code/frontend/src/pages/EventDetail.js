import { useState, useEffect } from 'react';
import EventListDetail from '../components/EventListDetail';
import { useLocation } from 'react-router-dom'


const Events = () => {
    const [events, setEvents] = useState(null);
    const [isPending, setIsPending] = useState(true);
    const [error, setError] = useState(null);
    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const eventIdParam = params.get('eventId') || '';
 
    useEffect(() => {
        fetch('http://3.67.188.187:8000/api/events/' + eventIdParam + '/', {
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
            {events && <EventListDetail events={events} title = "Event Detail"/>}
        </div>
     );
}
 
export default Events;