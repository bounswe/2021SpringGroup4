import { useState, useEffect } from 'react';
import EventList from '../components/EventList';


const EventSearch = () => {
   const [events, setEvents] = useState(null);
   const [isPending, setIsPending] = useState(true);
   const [error, setError] = useState(null);
 
    useEffect(() => {
        fetch('http://localhost:8000/api/search/event/location/', {
            method: 'POST',
            headers:{
                'Content-Type':'application/json'
            },
            body:JSON.stringify({'location':e.target.location.value, 'distance':e.target.distance.value})
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

            <form onSubmit={searchEvent}>
            
            
            <table className="table table-bordered">
                <tbody>
                    <tr>
                        <th>Location</th>
                        <td>
                            <input type="text" name="location" className="form-control" placeholder="City" />
                        </td>
                    </tr>
                    <tr>
                        <th>Distance</th>
                        <td>
                            <input type="text" name="distance" className="form-control" placeholder="Distance Km" />
                        
                        </td>
                    </tr>
                    <tr>
                        <td colSpan="2">
                            <input type="submit" value="Search" name="search" className="btn btn-dark"/>
                            
                        </td>
                    </tr>
                </tbody>
            </table> 

            </form>


        </div>
     );
}
 
export default EventSearch;