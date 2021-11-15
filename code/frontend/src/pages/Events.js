import { useState, useEffect } from 'react';
import EventList from '../components/EventList';


const Events = () => {
   const [events, setEvents] = useState(null);
    const [isPending, setIsPending] = useState(true);
    const [error, setError] = useState(null);
/*
 
    // hardcoded event data because of the db connection problem
    const [events, setEvents] = useState([
        {
            title: "Caddebostan Basketbol Maçı", 
            description: "Haftalık gerçekleştirdiğimiz 2 saatlik etkinlik", 
            date: "2021-10-20", 
            time: "18:00",
            duration: "02:00",
            maxPlayers: 8,
            applicants: 20,
            participants: 15,
            followers: 30,
            location: "İstanbul", 
            sportType: "Basketball", 
            id:1
        },
        {title: "Baltalimanı Halısaha Maçı", description: "10'a 10 gerçekleştiriyoruz", date: "2021-10-23", location: "İstanbul", sportType: "Football", id:2},
        {title: "Boğaziçi Üniversitesi Kortunda Tenis Maçı", description: "Orta seviye bir oyuncuyum. Haftada 2 saat oynamaya çalışıyorum.", date: "2021-10-25", location: "İstanbul", sportType: "Tennis", id:3},
        {title: "Bebek Sahil Koşu", description: "Pazar sabah erkenden bebek sahilde 1 saatlik koşu etkinliği yapıyorum", date: "2021-10-26", location: "İstanbul", sportType: "Running", id:4}
    ]);
*/
 //un-comment when the db connection work
 
    useEffect(() => {
        fetch('http://localhost:8000/api/events/', {
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
