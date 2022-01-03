import React from 'react'
import {useState, useEffect, useContext} from 'react'
import AuthContext from '../context/AuthContext'
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import { useLocation } from 'react-router-dom'
// import profile from '.../public/profile.png';

//Bu metodu kullanıyoruz
// use this method
function UserProfileSelect() {
    
    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const myusername = params.get('userName') || '';
    
    const data = []
    const [userdata, setUserdata] = useState({ username: "", email: "" , first_name: "", last_name: null, age: "" , location: "" });
    const [isPending, setIsPending] = useState(true);
    const [error, setError] = useState(null);
    
    

    useEffect(() => {
        fetch("http://3.67.188.187:8000/api/profiles/" + myusername + "/" , {
            method: 'GET'
        })
            .then(res => {
                console.log(res);
                if(!res.ok){
                    console.log(res);
                    throw Error('could not fetch the data for that resource');
                }
               
                return res.json();
            })
            .then(data => {

                setUserdata({
                    username: data.username,
                    email : data.email,
                    first_name:data.first_name,
                    last_name:data.last_name,
                    age : data.age,
                    about: data.about,
                    location: data.location,
                    going: data.going,
                    applied: data.applied,
                    profile_picture:data.profile_picture
                    
                });
                console.log("data taken with success");
                console.log(data)
                setIsPending(false);
                setError(null);
            })
            .catch(err => {
                setIsPending(false);
                setError(err.message);
            })
    }, [])




    return (
        <div>
            {  error && <div>{ error }</div>  }
            {  isPending && <div>Loading...</div> }




            <h1>User Profile</h1>
            

            <table className="table table-bordered" >
                

                <thead>

                     <tr>
                        <th>Profile photo: </th>
                        <th> 
                        { userdata.profile_picture  ?

                         <img src= {userdata.profile_picture} alt="Profile image" width="200" height="200" />
              
                        
                        :
                        <img src= "/profile.png" alt="Profile image" width="200" height="200" />   

                        }    
                            
                             
                            
                            
                         </th>
                       
                    </tr>


                    <tr>
                        <th>Username: </th>
                        <th>{userdata.username}</th>
                       
                    </tr>

                    <tr>
                        <th>E-mail: </th>
                        <th>{userdata.email}</th>
                       
                    </tr>

                    <tr>
                        <th>First Name: </th>
                        <th>{userdata.first_name}</th>
                       
                    </tr>

                    <tr>
                        <th>Last Name: </th>
                        <th>{userdata.last_name}</th>
                       
                    </tr>

                    <tr>
                        <th>Age: </th>
                        <th>{userdata.age}</th>
                       
                    </tr>

                    
                    <tr>
                        <th>About: </th>
                        <th>{userdata.about}</th>
                       
                    </tr>

                    <tr>
                        <th>Location: </th>
                        <th>{userdata.location}</th>
                       
                    </tr>

                    <tr>
                        <th>Going: </th>
                        <th>{userdata.going}</th>
                       
                    </tr>


                    <tr>
                        <th>Applied: </th>
                        <th>{userdata.applied}</th>
                       
                    </tr>

                </thead>
              
            </table>



            
            <div className="createAccount">

                <small> You could edit your profile </small>
                    <div>
                    <Link to="/editprofile">Edit Profile</Link>

                    </div>

            </div>
            
        </div>
    )
}

export default UserProfileSelect