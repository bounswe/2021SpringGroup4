import React from 'react'
import {useState, useEffect, useContext} from 'react'
import AuthContext from '../context/AuthContext'
import { useHistory } from 'react-router-dom'
import Axios from 'axios';

function EditUserProfile() {
    // const[profile_pictures, setProfilePicture] = useState("");
    
    var link="";

    let [profile_pictures, setProfilePictures]= useState( );
    const history = useHistory()
    let {authTokens, logoutUser} = useContext(AuthContext)
    let {myusername, user} = useContext(AuthContext);
    // let {updateProfile} = useContext(AuthContext)


    let updateProfile = async (e) => {
        console.log("link" + profile_pictures);
        e.preventDefault()
        let response = await fetch("http://3.67.188.187:8000/api/profiles/" + myusername + "/", {
            method:'PATCH',
            headers:{
                'Content-Type':'application/json',
                'Authorization':'Bearer ' + String(authTokens.access)
            },
            body:JSON.stringify({'first_name':e.target.first_name.value, 'last_name':e.target.last_name.value, 'age':e.target.age.value , 'about': e.target.about.value , 
            'location': e.target.location.value  , 'profile_picture': profile_pictures})  
        })
        
        
        let data = await response.json()

        
        if(response.status === 200){
            console.log("User Profile Data Updated")
            history.push('/profile')
        }
        
        else{
            console.log(response)
            alert('Please check your credentials')
        }
        
    }





    const[imageSelected, setImageSelected]= useState("");
    

    const uploadImage = () =>{
        const formData= new FormData();
        formData.append("file", imageSelected);
        formData.append("upload_preset", "nju7fu35");

        Axios.post(
            "https://api.cloudinary.com/v1_1/sportshub/image/upload",
            formData
        ).then( (response)=> { 
            // console.log(response);
            console.log("url linki:" + response.data.url)
            setProfilePictures( response.data.url )
            link=response.data.url;
            console.log("profile pictures new:  " + profile_pictures )
        
        });

        
    };

   
   




    return (
        
        <div>

                <tr> 
                        <th>Profile Photo</th>
                        <td>  
                    <input  type="file"  name="profile_picture" onChange = { (event)=> { setImageSelected(event.target.files[0]); } }  >
                    </input>
                    <button onClick={uploadImage}> Upload Image</button>
                        </td>

                 </tr>
            
            
            <form onSubmit={updateProfile}>
            
            
            <table className="table table-bordered">
                <tbody>
                    <tr>
                        <th>First Name</th>
                        <td>
                            <input type="text" name="first_name" className="form-control" placeholder="First Name" />
                        </td>
                    </tr>
                    <tr>
                        <th>Last Name</th>
                        <td>
                            <input type="text" name="last_name" className="form-control" placeholder="Last Name" />
                        
                        </td>
                    </tr>

                    <tr>
                        <th>Age</th>
                        <td>
                            <input type="text" name="age" className="form-control" placeholder="Age" />
                        
                        </td>
                    </tr>

                    <tr>
                        <th>About</th>
                        <td>
                            <input type="text" name="about" className="form-control" placeholder="" />
                        
                        </td>
                    </tr>

                    <tr>
                        <th>Location</th>
                        <td>
                            <input type="text" name="location" className="form-control" placeholder="Location" />
                        
                        </td>
                    </tr>

                    

                  
    
        


                    <tr>
                        <td colSpan="2">
                            <input type="submit" value="Edit" name="edit" className="btn btn-dark"/>
                            
                        </td>
                    </tr>
                </tbody>
            </table> 

            </form>

      


        </div>
    )
}

export default EditUserProfile
