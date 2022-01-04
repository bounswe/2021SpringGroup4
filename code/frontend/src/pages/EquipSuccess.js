import React, { Component } from 'react'
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';




export class EquipSuccess extends Component {
    render() {
        return (
            <div>
                <h2> You have been successfully created a new equipment post </h2>
                <p> You could find sport equipments</p>

                <h3>
                <Link to='/equipment' > Find/Buy Sport Equipment </Link>
                </h3>

                <p> You could sell other equipment</p>

                <h3>
                <Link to='/equipmentcreate' > Sell Equipment </Link>
                </h3>
                
            </div>
        )
    }
}

export default EquipSuccess 