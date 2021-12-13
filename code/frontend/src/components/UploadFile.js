//https://www.youtube.com/watch?v=Y-VgaRwWS3o
import React,{useState} from 'react'
import Axios from 'axios';


function UploadFile() {


    const[imageSelected, setImageSelected]= useState("");
    const uploadImage = () =>{
        const formData= new FormData();
        formData.append("file", imageSelected);
        formData.append("upload_preset", "nju7fu35");

        Axios.post(
            "https://api.cloudinary.com/v1_1/sportshub/image/upload",
            formData
        ).then( (response)=> { 
            console.log(response);

        
        });


    };


    return (
        <div>
            <input 
                type="file"
                onChange = { (event)=> {
                    setImageSelected(event.target.files[0]);
                } }    
                >
                </input>

                <button onClick={uploadImage}> Upload Image</button>
                <img src= "http://res.cloudinary.com/sportshub/image/upload/v1638880406/yigit/m8r3fxhkldb59jlhttjd.webp" />
        </div>
    )
}

export default UploadFile
