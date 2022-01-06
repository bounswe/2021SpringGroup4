import React from 'react'
import { useState, useEffect } from 'react';
import Cards from './Cards';

const UserPages= () => {

        const [pages, setPages] = useState(null);
        const [isPending, setIsPending] = useState(true);
        const [error, setError] = useState(null);
    
     
        useEffect(() => {
            fetch('http://localhost:8000/api/profiles/', {
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
                    setPages(data.map(d => d.body));
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
                {pages && <Cards page ={pages} title = "User Pages"/>}
            </div>
            
         );
    }
     
    

export default UserPages