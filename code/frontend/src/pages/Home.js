
import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import About from './About';
import Contact from './Contact';
import Login from './Login';
import Register from './Register';
import Equipment from './Equipment';
import CreateEvent from './CreateEvent';
import '../index.css'


function Home() {
    return (
       
        <Router>
           <div className="Appshome">
            <ul className="Appsul">
              <li>
                <Link to="/about">About Us</Link>
              </li>
              <li>
                <Link to="/contact">Contact Us</Link>
              </li>
              <li>
                <Link to="/register">Register</Link>
              </li>
              <li>
                <Link to="/login">Login</Link>
              </li>
              <li>
                <Link to="/equipment">Equipment</Link>
              </li>
              <li>
                <Link to="/createEvent">CreateEvent</Link>
              </li>
            </ul>
            <Switch>
              <Route exact path='/register' component={Register}></Route>
              <Route exact path='/about' component={About}></Route>
              <Route exact path='/contact' component={Contact}></Route>
              <Route exact path='/login' component={Login}></Route>
              <Route exact path='/equipment' component={Equipment}></Route>
              <Route exact path='/CreateEvent' component={CreateEvent}></Route>
            </Switch>
          </div>
        </Router>
        );
    
}

export default Home
