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
import Equipment from './Equipment';
import CreateEvent from './CreateEvent';
import EquipmentList from './EquipmentList';
import EventCreator from './EventCreator';

const HomePage = () => {


    
   // let {authTokens, logoutUser} = useContext(AuthContext)
    let {myusername, user} = useContext(AuthContext)


    return (


        <div>
            {user &&   <p>Hello user: {myusername}</p>}
            <p>You are logged to the home page!</p>

                        <li >
                            <Link to='/equipment' >
                                Sell Equipment 
                            </Link>
                        </li>
                        <li >
                            <Link to='/createevent' >
                                Create an Event
                            </Link>
                        </li>
                        <li >
                            <Link to='/equipmentList' >
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
        

         </div>

    )
}

export default HomePage