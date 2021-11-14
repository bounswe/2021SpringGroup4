
import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import Home from './pages/Home';
import About from './pages/About';
import Contact from './pages/Contact';
import Login from './pages/Login';
import './bootstrap.min.css';
import Register from './pages/Register';
import RegisterForm from './pages/RegisterForm';
import RegisteredUser from './pages/RegisteredUser';
import Home2 from './pages/Home2';
import Equipment from './pages/Equipment';
import CreateEvent from './pages/CreateEvent';
import EquipmentList from './pages/EquipmentList';



function App() {
  
  return (
   
    
      <Router>
        <Home2 />
        <Switch>
          <Route path='/about' exact component={About} />
          <Route path='/contact' component={Contact} />
          <Route path='/login' component={Login} />
          <Route path='/register' component={RegisterForm} />
          <Route path='/registereduser' component={RegisteredUser} />
          <Route path='/equipment' component={Equipment} />
          <Route path='/createEvent' component={CreateEvent} />
          <Route path='/equipmentList' component={EquipmentList} />
        </Switch>


      </Router>
    
  );
}

export default App;
