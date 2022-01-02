import React, { useState } from 'react'
import '../design/Card.css'
import Modal from './Modal'
import { Link} from 'react-router-dom';

function EquipmentCard({title,description,location,image_url,contact, sportType ,eqps}) {

    const [selectedEquipment, setSelectedEquipment] = useState(null)

    
    return (
        <div className="event-list">   
       


        <div className='Card' key={eqps.id} onClick= {()=> setSelectedEquipment(eqps)}>   
        <div className='upper-container'>
            <div className='image-container'>

                    { image_url ?

                                    <img src= {image_url} alt="Equipment image" width="100px" height="100px" />


                                    :
                                    <img src= "/profile.png" alt="Equipment image" width="100px" height="100px" />   

                    }    
   
                
            </div>

        </div>

        <div className='lower-container'> 
            
            <h3>{title}</h3>
            <h3>{description}</h3>
            <h4>{location}</h4>
            <h4>{contact}</h4>
            <h4>{sportType}</h4>
            

            
        
            <Link to ="./login" className = "btns"> Buy Equipment </Link>
            
            
        
            </div>

    
        </div>


        {selectedEquipment && <Modal isOpen={!!selectedEquipment } onClose={() => setSelectedEquipment(null)}> 
                    
                { selectedEquipment.image_url  ?

                        <img src= {selectedEquipment.image_url} alt="image" width="200px" height="200px" />


                            :
                        <img src= "/profile.png" alt="Profile image" width="200px" height="200px" />   

                } 

                <h2>Title: { selectedEquipment.title }</h2>
                <p> Description: { selectedEquipment.description }</p>
                <p> Location: { selectedEquipment.location }</p>
                <p> Contact: { selectedEquipment.contact }</p>
                <p> Sport Type : { selectedEquipment.sportType}</p>
            
                
              
               
                <div className = "button-block-modal">
                    <Link to ="./login" className = "btn btn-dark btn-lg">Buy now</Link>
                </div>    
            </Modal>
        }

         
         
         
         
        

         

    </div>


    )
}

export default EquipmentCard