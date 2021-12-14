import { useState, useEffect } from 'react';
import EventList from '../components/EventList';
import { useLocation } from 'react-router-dom'


const Events = () => {
    const [events, setEvents] = useState(null);
    const [isPending, setIsPending] = useState(true);
    const [error, setError] = useState(null);
    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const locationParam = params.get('location') || '';
    const distParam = params.get('dist') || '' ;
 
    useEffect(() => {
        fetch('http://3.67.188.187:8000/api/search/event/location/', {
            method: 'POST',
            body: JSON.stringify({
                'location': locationParam,
                'dist': distParam
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
                setEvents(data.map(d => d.body ));
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