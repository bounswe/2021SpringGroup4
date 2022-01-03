import { useState, useEffect } from 'react';
import EventList from '../components/EventList';

const Events = () => {
    const [events, setEvents] = useState(null);
    const [isPending, setIsPending] = useState(true);
    const [error, setError] = useState(null);

 
    useEffect(() => {
        fetch('http://3.67.188.187:8000/api/events/', {
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
            {events && <EventList events={events} title = "All Events"/>}
        </div>
        
     );
}
 
export default Events;