
import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import Home from './pages/Home';
import About from './pages/About';
import Contact from './pages/Contact';
import Login from './pages/Login';
import './bootstrap.min.css';
import Register from './pages/Register';
import Home2 from './pages/Home2'
import RegisterForm from './pages/RegisterForm';
import RegisteredUser from './pages/RegisteredUser';
import EquipmentList from './pages/EquipmentList';
import Home2backgroundfinal from './pages/Home2bgfinal';
import Equipment from './pages/Equipment';
import CreateEvent from './pages/CreateEvent';



function App() {
  
  return (
   
    
      <Router>
        <Home2 />
        <Home2backgroundfinal />
        <Switch>
          <Route path='/about' exact component={About} />
          <Route path='/contact' component={Contact} />
          <Route path='/login' component={Login} />
          <Route path='/register' component={RegisterForm} />
          <Route path='/registereduser' component={RegisteredUser} />
          <Route path='/equipment' component={Equipment} />
          <Route path='/event' component={CreateEvent} />
        </Switch>


      </Router>
    
  );
}

export default App;
