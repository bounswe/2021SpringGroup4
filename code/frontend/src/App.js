
import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import Home from './pages/Home';
import About from './pages/About';
import Contact from './pages/Contact';
import Login from './pages/Login';
import './index.css'

function App() {
  
  return (
    <Router>
           <div >
            <ul >
              <li>
                <Link to="/">Home</Link>
              </li>
              <li>
                <Link to="/about">About Us</Link>
              </li>
              <li>
                <Link to="/contact">Contact Us</Link>
              </li>
              <li>
                <Link to="/login">Login</Link>
              </li>
            </ul>
            <Switch>
              <Route exact path='/' component={Home}></Route>
              <Route exact path='/about' component={About}></Route>
              <Route exact path='/contact' component={Contact}></Route>
              <Route exact path='/login' component={Login}></Route>
            </Switch>
          </div>
     </Router>
  );


}

export default App;
