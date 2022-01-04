import React, {useState, useEffect, useContext} from 'react'
import AuthContext from '../context/AuthContext'
import { useHistory } from 'react-router-dom'

function EquipmentSearch() {

    const history = useHistory()
    let {authTokens, logoutUser} = useContext(AuthContext)
    const [isPending, setIsPending] = useState(true);
    const [error, setError] = useState(null);
    const [eqData, seteqData] = useState({ id: "", owner: "" , title: "", description: "", location: "",contact:"" , sportType:"" });

    const [equips,setEqs] =useState(false);


    let readEquip = async (e) => {
        e.preventDefault()
        console.log(`
        --SUBMITTING--
        UserId: ${e.target.userId.value}
        
      `);
        

        let response = await fetch("http://3.67.188.187:8000/api/equipment/" + e.target.userId.value + "/" , {
            method:'GET',
           
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
            setEqs(true);

            seteqData({
                id: data.id,
                owner : data.owner,
                title:data.title,
                description:data.description,
                location: data.location,
                contact: data.contact,
                sportType: data.sportType,
                
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



    return(
        <div>
            <h1>Search Equipment</h1>

                <form onSubmit={readEquip}>

                <table className="table table-bordered">
                    <tbody>
                     <tr>
                        <th>Equipment Id</th>
                        <td>
                            <input type="text" name="userId" className="form-control" placeholder="Id" />
                
                        </td>
                    </tr>

                    <tr>
                        <td colSpan="2">
                            <input type="submit" value="Find" name="find" className="btn btn-dark"/>

                         </td>
                     </tr>

                     </tbody>
                </table> 


            </form>    


                
                { equips  ?
                        <table>
                            <tr>Owner : {eqData.owner}  </tr>
                           <tr> Title :  {eqData.title}  </tr> 
                            <tr>Description: {eqData.description} </tr>
                            <tr> Location:  {eqData.location} </tr>
                            <tr> Contact:   {eqData.contact} </tr>
                            <tr>Sport Type: {eqData.sportType}</tr>
                        </table>   
                        
                        :
                  <p> No data</p> 

                }    
                            
                             
                            
                            
                   



        </div>


    )

}


export default EquipmentSearch