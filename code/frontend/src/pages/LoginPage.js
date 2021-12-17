import React, {useContext} from 'react'
import AuthContext from '../context/AuthContext'
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import RegisterForm from './RegisterForm';

const LoginPage = () => {
    let {loginUser} = useContext(AuthContext)
    return (
        <div>

            <form onSubmit={loginUser}>
            
            
            <table className="table table-bordered">
                <tbody>
                    <tr>
                        <th>UserName</th>
                        <td>
                            <input type="text" name="username" className="form-control" placeholder="Enter Username" />
                        </td>
                    </tr>
                    <tr>
                        <th>Password</th>
                        <td>
                            <input type="password" name="password" className="form-control" placeholder="******" />
                        
                        </td>
                    </tr>
                    <tr>
                        <td colSpan="2">
                            <input type="submit" value="Login" name="login" className="btn btn-dark" />
                            
                        </td>
                    </tr>
                </tbody>
            </table> 

            </form>
           
            
            <div className="createAccount">

                <small> Or Sign Up Using </small>
                    <div>
                    <Link to="/register">Sign Up</Link>

                     </div>
                
            </div>


        </div>
    )
}

export default LoginPage