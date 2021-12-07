import React from 'react'
import {useState, useEffect, useContext} from 'react'
import AuthContext from '../context/AuthContext'

function EditUserProfile() {
   
    let {loginUser} = useContext(AuthContext)
   
   
    return (
        
        <div>
            
            
            <form onSubmit={loginUser}>
            
            
            <table className="table table-bordered">
                <tbody>
                    <tr>
                        <th>First Name</th>
                        <td>
                            <input type="text" name="username" className="form-control" placeholder="First Name" />
                        </td>
                    </tr>
                    <tr>
                        <th>Last Name</th>
                        <td>
                            <input type="text" name="lastname" className="form-control" placeholder="Last Name" />
                        
                        </td>
                    </tr>
                    <tr>
                        <td colSpan="2">
                            <input type="submit" value="edit" name="edit" className="btn btn-dark"/>
                            
                        </td>
                    </tr>
                </tbody>
            </table> 

            </form>


        </div>
    )
}

export default EditUserProfile
