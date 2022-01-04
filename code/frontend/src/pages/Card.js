import React, { useState } from 'react'
import '../design/Card.css'
import Modal from '../components/Modal'
import { Link} from 'react-router-dom';

function Card({username,email,firstname,location,about,profile_picture, user}) {

    const [selectedUser, setSelectedUser] = useState(null)

    
    return (
        <div className="event-list">   
       


        <div className='Card' key={user.id} onClick= {()=> setSelectedUser(user)}>   
        <div className='upper-container'>
            <div className='image-container'>

                    { profile_picture  ?

                                    <img src= {profile_picture} alt="Profile image" width="100px" height="100px" />


                                    :
                                    <img src= "/profile.png" alt="Profile image" width="100px" height="100px" />   

                    }    
   
                
            </div>

        </div>

        <div className='lower-container'> 
            
            <h3>{username}</h3>
            <h3>{email}</h3>
            <h4>{firstname}</h4>
            <h4>{location}</h4>
            <p> {about}</p>

            
        
            <Link to ="./login" className = "btns"> Visit Profile </Link>
            
            
        
            </div>

    
        </div>


        {selectedUser && <Modal isOpen={!!selectedUser} onClose={() => setSelectedUser(null)}> 
                    
                { selectedUser.profile_picture  ?

                        <img src= {selectedUser.profile_picture} alt="Profile image" width="200px" height="200px" />


                            :
                        <img src= "/profile.png" alt="Profile image" width="200px" height="200px" />   

                } 

                <h2>Username: { selectedUser.username }</h2>
                <p> Email: { selectedUser.email }</p>
                <p> First Name: { selectedUser.first_name }</p>
                <p> Last Name: { selectedUser.last_name }</p>
                <p> Age : { selectedUser.age }</p>
                <p>About: { selectedUser.about }</p>
                <p> Location: { selectedUser.location }</p>
                <p> Going: { selectedUser.going }</p>
                <p> Applied: { selectedUser.applied }</p>
                
              
               
                <div className = "button-block-modal">
                    <Link to ="./login" className = "btn btn-dark btn-lg">Give a badge!</Link>
                </div>    
            </Modal>
        }

         
         
         
         
        

         

    </div>


    )
}

export default Card