
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
import EquipmentCreator from './pages/EquipmentCreator';
import EquipmentList from './pages/EquipmentList';
import Events from './pages/Events';
import EventCreator from './pages/EventCreator';
import { EventSuccess } from './pages/EventSucces';
import SearchEvents from './pages/SearchEvents';
import UploadFile from './components/UploadFile';
import UserProfile from './pages/UserProfile';
import EditUserProfile from './pages/EditUserProfile';
import Maps from './components/Maps';
import UserGeolocation from './components/UserGeolocation';
import EventListDetail from './components/EventListDetail';
import EventDetail from './pages/EventDetail';
import UserProfileSelect from './pages/UserProfileSelect';
import EquipSuccess from './pages/EquipSuccess';
import EquipmentSearch from './pages/EquipmentSearch';
import EquipmentSelect from './pages/EquipmentSelect';


import UserCards from './pages/UserCards';



function App() {
  


  return (
    
      <Router>
      
        <AuthProvider>
          <Home/>
          <Header/>
          <PrivateRoute component={HomePage} path="/" exact/>
          <Route component={LoginPage} path="/login"/>
          <Route path='/createevent' component={EventCreator} />


          <Route path='/eventsuccess' component={EventSuccess} />


        <Route path='/home' exact component={Home} />
        <Route path='/about' exact component={About} />
        <Route path='/contact' component={Contact} />
        <Route path='/register' component={RegisterForm} />
        <Route path="/registereduser" component={RegisteredUser}/>

        <Route path='/equipment' component={Equipment} />
        <Route path='/equipsuccess' component={EquipSuccess} />
        <Route path='/equipmentsearch' component={EquipmentSearch} />
        <Route path='/equipmentcreate' component={EquipmentCreator} />
        <Route path='/equipmentSelect' component={EquipmentSelect} />
        
        <Route path='/equipmentList' component={EquipmentList} />
        <Route path='/events' component={Events} />
        <Route path='/eventListDetail' component={EventListDetail} />
        <Route path='/eventDetail' component={EventDetail} />
        <Route path='/searchEvents' component={SearchEvents} />
        <Route path='/fileupload' component={UploadFile} />

        <Route path='/profile' component={UserProfile} />
        <Route path='/editprofile' component={EditUserProfile} />
        <Route path='/userProfileSelect' component={UserProfileSelect} />

        
        <Route path='/googlemap' component={Maps} />
        <Route path='/geolocation' component={UserGeolocation} />
        
        <Route path='/usercards' component={UserCards} />
        


        </AuthProvider>

      
      
      </Router>


 
    
  );
}

export default App;
