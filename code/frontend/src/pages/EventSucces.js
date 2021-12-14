
import React, { Component } from 'react'
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import Login from './Login';
import EventCreator from './EventCreator';


export class EventSuccess extends Component {
    render() {
        return (
            <div>
                <h2> You have been successfully created a new event </h2>
                <p> You could explore new events</p>

                <h3>
                <Link to='/events' > Explore new events </Link>
                </h3>

                <p> You could create new events</p>

                <h3>
                <Link to='/createevent' > Create new events </Link>
                </h3>
                
            </div>
        )
    }
}

export default EventSuccess