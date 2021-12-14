import React, {useContext} from 'react'
import { Link } from 'react-router-dom'
import AuthContext from '../context/AuthContext'

const Header = () => {
    // let {user, logoutUser} = useContext(AuthContext)
    let {myusername, user,logoutUser} = useContext(AuthContext)
   
    return (
        <div>
            <Link to="/" >Home</Link>
            <span> | </span>
            {user ? (
                  <button onClick={logoutUser}  > Logout  </button> 
            ): (
                <Link to="/login" >Login</Link> ,
                <Link to="/register" >Register</Link>
            )}

         
        
         

           
        </div>
    )
}

export default Header