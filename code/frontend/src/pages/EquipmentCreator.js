import React, {useState, useEffect, useContext} from 'react'
import AuthContext from '../context/AuthContext'
import { useHistory } from 'react-router-dom'
import Axios from 'axios';
import '../design/Equipment.css';

function EquipmentCreator() {

    const [ profileImg, setprofileImg] = useState('https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png');

    let [preview, setPreview] = useState();

    const [image_url, setImageUrl]= useState( );


    const history = useHistory()
    let {authTokens, logoutUser} = useContext(AuthContext)
    
    let createEquip = async (e) => {
        e.preventDefault()
        console.log(`
        --SUBMITTING--
        Title: ${e.target.title.value}
        Description: ${e.target.description.value}
        Location: ${e.target.location.value}
        Contact: ${e.target.contact.value}
        Sport Type : ${e.target.sportType.value}
        Authtoken : ${authTokens.access}
      `);
      console.log("link" + image_url);

        let response = await fetch('http://3.67.188.187:8000/api/equipment/', {
            method:'POST',
            headers:{
                'Content-Type':'application/json',
                'Authorization':'Bearer ' + String(authTokens.access)
            },
            body:JSON.stringify({'title':e.target.title.value, 'description':e.target.description.value, 'location':e.target.location.value , 'contact': e.target.contact.value , 
            'sportType': e.target.sportType.value , 'image_url': image_url })
        })
        
        let data = await response.json()
        
        console.log('Response Status' + response.status );

        if(response.status === 201){
            console.log('Successfully submitted event data' );
            history.push('/equipsuccess')
        }

        else if(response.status==500){
            console.log("Data is too long, select another place");
            alert("Data is too long, please select another place");
        }
        
        else{
            alert('Please check your credentials..Be careful')
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
            setImageUrl( response.data.url );

            console.log("Image Url new:  " + image_url )
            
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
        <h1>Sell Your Equipment </h1>


        <div className="pages">
                                <div className="container">
                                    <h1 className="heading">Add Image</h1>
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
                                        Choose Photo
                                    </label>

                                    <button onClick={uploadImage}> Upload Image</button>
                                    { image_url ? <label>File Uploaded</label> : <label>No file</label> } 
                                    </div>

                                 

                                </div>
                
        </div>






        <form onSubmit={createEquip}>
    
    
    <table className="table table-bordered">
        <tbody>
            <tr>
                <th>Title</th>
                <td>
                    <input type="text" name="title" className="form-control" placeholder="Title" />
                </td>
            </tr>
            <tr>
                <th>Description</th>
                <td>
                    <input type="text" name="description" className="form-control" placeholder="Description" />
                
                </td>
            </tr>

            <tr>
                <th>Location</th>
                <td>
                    <input type="text" name="location" className="form-control" placeholder="Location" />
                
                </td>
            </tr>


            <tr>
                <th>Contact</th>
                <td>
                    <input type="text" name="contact" className="form-control" placeholder="Contact" />
                
                </td>
            </tr>


            <tr>
                <th>Sport Type</th>
                <td>
                    <input type="text" name="sportType" className="form-control" placeholder="Sport Type" />
                
                </td>
            </tr>

        

            <tr>
                <td colSpan="2">
                    <input type="submit" value="Create" name="create" className="btn btn-dark"/>
                    
                </td>
            </tr>
        </tbody>
    </table> 
                                      
    </form>
        
</div>


    )
}

export default EquipmentCreator