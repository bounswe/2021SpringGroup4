
import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import Home from './pages/Home';
import About from './pages/About';
import Contact from './pages/Contact';
import Login from './pages/Login';
import './bootstrap.min.css';
import Register from './pages/Register';
import Home2 from './pages/Home2'



function App() {
  
  return (
    <>
      <Router>
        <Home2 />
        <Switch>
          <Route path='/about' exact component={About} />
          <Route path='/contact' component={Contact} />
          <Route path='/login' component={Login} />
          <Route path='/register' component={Register} />
        </Switch>
      </Router>
    </>
  );
}

export default App;
