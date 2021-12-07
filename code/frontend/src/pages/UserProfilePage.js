import React, { Component } from 'react'
import {useState, useEffect, useContext} from 'react'
import AuthContext from '../context/AuthContext'





export class UserProfilePage extends Component {
    

    constructor(){
        super();
        this.state={
            data:[]
            
        };
        
    
    }



    fetchData(){
        const myusername = 'yigit'
       
        fetch("http://3.67.188.187:8000/api/profiles/" + myusername + "/" ,{ 
            method:'GET', } )

            
        .then(response=>response.json() )
        .then((data) =>{
            this.setState({
                data:data
            });
        } );

    }

    componentDidMount(){
        this.fetchData();
    }



    render() {
        const profiledata = this.state.data;
   

        return (
            <div>
                <table className="table table-bordered" >
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Username</th>
                        <th>E-mail</th>
                        <th>Age</th>
                        <th>Location</th>
                    </tr>
                </thead>
                <tbody>
                     <tr>
                        <th>{ profiledata.id } </th>
                        <th>{ profiledata.username }</th>
                        <th>{ profiledata.email }</th>
                        <th>{ profiledata.age } </th>
                        <th>{ profiledata.location }</th>
                    </tr>

                    
                </tbody>
            </table>
                    <button >Edit User Profile</button>
            </div>
        )
    }
}

export default UserProfilePage
