import React,{useState} from 'react'
import '../design/Card.css'
import { Link, useHistory } from 'react-router-dom';

const Cards = ({pages, title}) => {


   // const [username,setUserName] = useState('User Name');
   // const [email,setEmail] = useState('User Email');
   // const [firstname,setName] = useState('Your Name');
   // const [location , setLocation] =useState("Your Location");
   // const [about , setAbout] = useState("About");

    return ( pages.map((page) => (
        <div className='Card'>   
            <div className='upper-container'>
                <div className='image-container'>
                    <img src="/profile.png" alt=""  height="100px" width="100px" />
                </div>

            </div>

            <div className='lower-container'> 
                
                <h3>Username: {page.username}</h3>
                <h3>Email: {page.email}</h3>
                <h4>Firstname: {page.firstname}</h4>
                <h4>Location: {page.location}</h4>
                <p> About: {page.about}</p>
                <button>Visit Profile</button>
            
            </div>


            
        
        </div>  ))
 

    );
}

export default Cards;


    
