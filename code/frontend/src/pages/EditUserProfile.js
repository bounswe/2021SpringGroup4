import React from 'react'
import {useState, useEffect, useContext} from 'react'
import AuthContext from '../context/AuthContext'
import { useHistory } from 'react-router-dom'
import Axios from 'axios';
import '../design/Image.css';

function EditUserProfile() {
    
    
    var link="";

    const [ profileImg, setprofileImg] = useState('https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png');

    let [preview, setPreview] = useState();

    const [profile_pictures, setProfilePictures]= useState( );
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
            alert('User Profile succesfully updated..')
            history.push('/profile')
        }
        
        else{
            console.log(response)
            alert('Please fill out the form')
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

            if(response.status === 200){
            // console.log(response);
            console.log("url linki:" + response.data.url)
            setProfilePictures( response.data.url );

            console.log("profile pictures new:  " + profile_pictures )
            
            }

            else{
                console.log(response.data);
                alert('File could not uploaded, no response from remote server');
            }
            
            
        });

        
    };



    const imageHandler = (e) => {
        const reader = new FileReader();
        reader.onload = () =>{
          if(reader.readyState === 2){
            setprofileImg (reader.result);
            setPreview(reader.result);
          }
        }
        console.log("selected image" + imageSelected.data);
        reader.readAsDataURL( e );
    } ;   
   
   




    return (
        

        <div>
                

                <div className="page">
                                <div className="container">
                                    <h1 className="heading">Add your Image</h1>
                                    <div className="img-holder">
                                        {preview ?     
                                          <img src={preview} alt="" id="img" className="img" />
                                        :      
                                           <img src={profileImg} alt="" id="img" className="img" />
                                        }
                                        
                                    </div>
                                    <input type="file" accept="image/*" name="image-upload" id="input" onChange={ (event)=> { setImageSelected(event.target.files[0]); imageHandler(event.target.files[0]); } } />
                                    <div className="label">
                        <label className="image-upload" htmlFor="input">
                                        <i className="material-icons"></i>
                                        Choose your Photo
                                    </label>

                                    <button onClick={uploadImage}> Upload Image</button>
                                    {profile_pictures ? <label>File Uploaded</label> : <label>No file</label> } 
                                    </div>

                                 

                                </div>
                
                </div>
            



            
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
                            <input type="submit" value="Update Profile" name="edit" className="btn btn-dark"/>
                            
                        </td>
                    </tr>
                </tbody>
            </table> 

            </form>

      


        </div>
    )
}

export default EditUserProfile
