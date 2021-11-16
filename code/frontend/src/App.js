
import './App.css';



import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import PrivateRoute from './utils/PrivateRoute'
import { AuthProvider } from './context/AuthContext'

import Home from './pages/Home'
import HomePage from './pages/HomePage'
import LoginPage from './pages/LoginPage'
import Header from './components/Header'
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
import Events from './pages/Events';
import EventCreator from './pages/EventCreator';
import { EventSuccess } from './pages/EventSucces';

function App() {
  


  return (
    
      <Router>
      <Home/>
        <AuthProvider>
          <Header/>
          <PrivateRoute component={HomePage} path="/" exact/>
          <Route component={LoginPage} path="/login"/>
          <Route path='/createevent' component={EventCreator} />


          <Route path='/eventsuccess' component={EventSuccess} />
          
        </AuthProvider>

        <Switch>
          <Route path='/home' exact component={Home} />
          <Route path='/about' exact component={About} />
          <Route path='/contact' component={Contact} />
          <Route path='/register' component={RegisterForm} />
          <Route path="/registereduser" component={RegisteredUser}/>

          <Route path='/equipment' component={Equipment} />
          
          <Route path='/equipmentList' component={EquipmentList} />
          <Route path='/events' component={Events} />


          
        </Switch>
      </Router>


 
    
  );
}

export default App;
