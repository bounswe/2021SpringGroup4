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

const HomePage = () => {


    let [notes, setNotes] = useState([])
    let {authTokens, logoutUser} = useContext(AuthContext)



    const [click, setClick] = useState(false);

    const [button,setButton]= useState(true);

    const clickHandler = () => setClick(!click); 

    const closeMobileMenu = () => setClick(false);

    const showButton = () => {
        if(window.innerWidth <= 960) {
            setButton(false);
        } else {
            setButton(true);
        }
    };

    useEffect(() => {
        showButton();
      }, []);

    window.addEventListener('resize', showButton);


  

    let getNotes = async() =>{
        let response = await fetch('http://localhost:8000/api/events/', {
            method:'GET',
            headers:{
                'Content-Type':'application/json',
                'Authorization':'Bearer ' + String(authTokens.access)
            }
        })
        let data = await response.json()

        if(response.status === 200){
            setNotes(data)
        }else if(response.statusText === 'Unauthorized'){
            logoutUser()
        }
        
    }

    return (


        <div>
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
        

         </div>

    )
}

export default HomePage