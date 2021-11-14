import React, { Component } from 'react'
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import Login from './Login';

export class RegisteredUser extends Component {
    render() {
        return (
            <div>
                <h2> You have been successfully registered to the website </h2>
                <p> Please Log IN  to the website</p>

                <h3>
                <Link to='/login' > Login </Link>
                </h3>
                
            </div>
        )
    }
}

export default RegisteredUser