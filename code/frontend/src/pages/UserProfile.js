import React from 'react'
import {useState, useEffect, useContext} from 'react'
import AuthContext from '../context/AuthContext'
import { useHistory, BrowserRouter as Router, Route, Link, Switch} from 'react-router-dom';
import Events from './SearchEvents'
// import profile from '.../public/profile.png';

//Bu metodu kullanıyoruz
// use this method
function UserProfile() {
    const history = useHistory()
    const [selectedUser, setSelectedUser] = useState(null)
    const onFindUser = ()=> {
        if(!!selectedUser){
            const params = new URLSearchParams()
            params.append('userName', selectedUser)
            history.push('/userProfileSelect?' + params)
        }
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
                profile_picture:data.profile_picture,
                badges: data.badges
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
    }

    
    let {myusername, user, authTokens} = useContext(AuthContext);
    const data = []
    const [userdata, setUserdata] = useState({ username: "", email: "" , first_name: "", last_name: null, age: "" , location: "" });
    const [isPending, setIsPending] = useState(true);
    const [error, setError] = useState(null);
    const [badgePic,setBadgePic] = useState(null);
    const onSelectBadge = (selectedItem)=> {
        setBadgePic(selectedItem)
    }
    const onGiveBadge = ()=> {
        fetch('http://3.67.188.187:8000/api/badges/' ,{
            method:'POST',
            headers:{
                'Content-type': 'application/json; charset=UTF-8',
                'Authorization':'Bearer ' + String(authTokens.access)
            },
            body:JSON.stringify({'owner':myusername, 'event':1, 'type':badgePic})
        })
        .then(res => {
            console.log(res);
            if(!res.ok){
                console.log(res);
                throw Error('could not fetch the data for that resource');
            }
            return res.json();
        })
        alert(badgePic+"  badge granted successfully!")
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
                profile_picture:data.profile_picture,
                badges: data.badges
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
    }
    

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
                    profile_picture:data.profile_picture,
                    badges: data.badges
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
                   <h2>My Badges</h2>
                    { userdata.badges && 
                    userdata.badges.map( (badg)  => 
                    <tr key={badg.id}>
                         <p style={{display:"block", cursor:"pointer"}} onClick={() => {
                            setSelectedUser(badg.giver);
                            onFindUser();
                        }}> {badg.giver.toUpperCase()}</p>
                        <tr>                        
                        Type : {badg.type}

                        {  badg.type === "Gentleman" ?  <img src= "/Gentleman.png" alt="Bage image" width="100" height="100" />   :
                            badg.type === "Friendly" ?  <img src= "/Friendly.png" alt="Badge image" width="100" height="100" />  :
                            badg.type === "Master" ?  <img src= "/Master.png" alt="Badge image" width="100" height="100" />  :
                            badg.type === "Novice" ?  <img src= "/Novice.png" alt="Badge image" width="100" height="100" />  :
                            badg.type === "Top Organizer" ?  <img src= "/Top Organizer.png" alt="Badge image" width="100" height="100" />  :
                            badg.type === "Sore Loser" ?  <img src= "/Sore Loser.png" alt="Badge image" width="100" height="100" />  :
                            badg.type === "Crybaby" ?  <img src= "/Crybaby.png" alt="Badge image" width="100" height="100" />  :
                            badg.type === "Gentleman" ?  <img src= "/Gentleman.png" alt="Bage image" width="100" height="100" /> :
                            badg.type === "Skilled" ?  <img src= "/Skilled.png" alt="Bage image" width="100" height="100" /> :
                            <img src= "/empty.png" alt="Profile image" width="100" height="100" />   

                        }
                        </tr>
                        
                        
                    </tr>
                        )
                    }

                </tr>

                </thead>
              
            </table>

 
           <Events></Events>

            <div className="createAccount">

                <small> You could edit your profile </small>
                    <div>
                    <Link to="/editprofile">Edit Profile</Link>

                    </div>

            </div>
            
        </div>
    )
}

export default UserProfile
