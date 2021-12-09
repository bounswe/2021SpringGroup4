import React, {useState, useEffect, useContext} from 'react'
import AuthContext from '../context/AuthContext'
import { useHistory } from 'react-router-dom'
import Maps from '../components/Maps'

const EventCreator = () => {   


    const history = useHistory()
    let {authTokens, logoutUser} = useContext(AuthContext)
    
    let createEvent = async (e) => {
        e.preventDefault()
        console.log(`
        --SUBMITTING--
        Title: ${e.target.title.value}
        Description: ${e.target.title.value}
        Time: ${e.target.time.value}
        Duration: ${e.target.duration.value}
        Authtoken : ${authTokens.access}
      `);

        let response = await fetch('http://3.67.188.187:8000/api/events/', {
            method:'POST',
            headers:{
                'Content-Type':'application/json',
                'Authorization':'Bearer ' + String(authTokens.access)
            },
            body:JSON.stringify({'title':e.target.title.value, 'description':e.target.description.value, 'date':e.target.date.value , 'time': e.target.time.value , 
            'duration': e.target.duration.value , 'location': e.target.location.value ,'sportType': e.target.sportType.value, 'maxPlayers': e.target.maxPlayers.value  })
        })
        
        let data = await response.json()
        
        console.log('Response Status' + response.status );

        if(response.status === 201){
            console.log('Successfully submitted event data' );
            history.push('/eventsuccess')
        }
        
        else{
            alert('Please check your credentials..Be careful')
        }
        
    }



    return (
            <div>
                <h1>Create New Event</h1>

                <form onSubmit={createEvent}>
            
            
            <table className="table table-bordered">
                <tbody>
                    <tr>
                        <th>Title</th>
                        <td>
                            <input type="text" name="title" className="form-control" placeholder="Title" />
                        </td>
                    </tr>
                    <tr>
                        <th>Description</th>
                        <td>
                            <input type="text" name="description" className="form-control" placeholder="Event Description" />
                        
                        </td>
                    </tr>

                    <tr>
                        <th>Date</th>
                        <td>
                            <input type="date" name="date" className="form-control" placeholder="Date" />
                        
                        </td>
                    </tr>

                    <tr>
                        <th>Time</th>
                        <td>
                            <input type="time" name="time" className="form-control" placeholder="Time" />
                        
                        </td>
                    </tr>

                    <tr>
                        <th>Duration</th>
                        <td>
                            <input type="time" name="duration" className="form-control" placeholder="Duration" />
                        
                        </td>
                    </tr>

                    <tr>
                        <th>Location</th>
                        <td>
                            <input type="text" name="location" className="form-control" placeholder="Location" />
                        
                        </td>
                    </tr>

                   

                    <tr>
                        <th>Sport Type</th>
                        <td>
                            <input type="text" name="sportType" className="form-control" placeholder="Sport Type" />
                        
                        </td>
                    </tr>

                    <tr>
                        <th>Maximum Players</th>
                        <td>
                            <input type="text" name="maxPlayers" className="form-control" placeholder="Maximum Players" />
                        
                        </td>
                    </tr>

                    
                    <tr>
                        <td colSpan="2">
                            <input type="submit" value="Create" name="create" className="btn btn-dark"/>
                            
                        </td>
                    </tr>
                </tbody>
            </table> 

            </form>
                
        </div>
        )
    
}

export default EventCreator

