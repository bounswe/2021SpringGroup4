import React, {useState, useEffect} from 'react';
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
function Home2() {
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

    return (
        <div>
            <nav className="home2">
                <div className="home2-container">
                    <Link to="/" className="home2-logo" onClick= {closeMobileMenu}>
                        SportsHub <i className="fab fa-typo3"/>
                    </Link>
                    <div className='menu-icon' onClick={clickHandler}>
                        <i className={click ? 'fas fa-times' : 'fas fa-bars'} />
                    </div>
                    <ul className={click ? 'home2-menu active' : 'home2-menu'}>    
                        <li className='home2-item'>
                            <Link to='/about' className='home2-links' onClick={closeMobileMenu}>
                                About
                            </Link>
                        </li>

                        <li className='home2-item'>
                            <Link to='/contact' className='home2-links' onClick={closeMobileMenu}>
                                Contact
                            </Link>
                        </li>

                        <li className='home2-item'>
                            <Link to='/login' className='home2-links' onClick={closeMobileMenu}>
                                Login
                            </Link>
                        </li>

                        <li className='home2-item'>
                            <Link to='/register' className='home2-links' onClick={closeMobileMenu}>
                                Register
                            </Link>
                        </li>
                        <li className='home2-item'>
                            <Link to='/equipment' className='home2-links' onClick={closeMobileMenu}>
                                Equipment
                            </Link>
                        </li>
                        <li className='home2-item'>
                            <Link to='/createEvent' className='home2-links' onClick={closeMobileMenu}>
                                Event
                            </Link>
                        </li>

                    </ul>
                 
                </div>

            </nav>

            

     </div>
            
    );
}

export default Home2
