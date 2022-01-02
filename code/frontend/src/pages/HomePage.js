import React, {useState, useEffect, useContext} from 'react'
import AuthContext from '../context/AuthContext'
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';

import { Button } from '../components/Button';
import './Home2.css';
import About from './About';
import Contact from './Contact';
import Login from './Login';
import Register from './Register';
import RegisterForm from './RegisterForm';

import CreateEvent from './CreateEvent';
import EquipmentList from './EquipmentList';
import EventCreator from './EventCreator';
import UserCard from "./UserCards";
import UserPages from './UserPages';


const HomePage = () => {


    
   // let {authTokens, logoutUser} = useContext(AuthContext)
    let {myusername, user} = useContext(AuthContext)


    return (


        <div>
            {user &&   <p>Hello : {myusername}</p>}
            <p>You are logged to the home page!</p>

                        <li >
                            <Link to='/equipmentcreate' >
                                Sell Equipment 
                            </Link>
                        </li>
                        <li >
                            <Link to='/createevent' >
                                Create an Event
                            </Link>
                        </li>
                        <li >
                            <Link to='/equipment' >
                                Find an Equipment 
                            </Link>
                        </li>

                        <li >
                            <Link to='/events' >
                                Explore Events 
                            </Link>
                        </li>

                        <li >
                            <Link to='/profile' >
                               User Profile Page
                            </Link>
                        </li>

                        <li >
                            <Link to='/googlemap' >
                               Google Map Page
                            </Link>
                        </li>

                        <li >
                            <Link to='/geolocation' >
                               User Geolocation
                            </Link>
                        </li>
                        

                        <li >
                            <Link to='/equipmentsearch' >
                               Equipment Search
                            </Link>
                        </li>

                

                        <li >
                            <Link to='/usercards' >
                               User Cards
                            </Link>
                        </li>

                    
                        
        

         </div>

    )
}

export default HomePage