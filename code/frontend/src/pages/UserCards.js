import React, { Component } from "react";
import Card from './Card'
import '../design/UserCards.css'


class UserCards extends Component {
    constructor(){
        super();
        this.state={
            data:[]
        };
    }

    fetchData(){
        fetch('http://3.67.188.187:8000/api/profiles/', { 
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
        const userdata= this.state.data;
        const rows=userdata.map((userd)  => 
            <tr key={userd.id}>
                
                <td>{userd.username}</td>
                <td>{userd.email}</td>
                <td>{userd.location}</td>
                <td>{userd.age}</td>
                <td>{userd.about}</td>
                <td>{userd.going}</td>
                <td>{userd.applied}</td>
                
            </tr>
            
        );

        return (
            <div className="events">
                { userdata.map((userd)  => 
                    <Card username={userd.username}  email= {userd.email}   firstname={userd.firstname}  location={userd.location} about={userd.about}  profile_picture={userd.profile_picture} user={userd} />
                ) }
                


            {/*
                <table className="table table-bordered" >
                <thead>
                    <tr>
                        
                        <th>Username</th>
                        <th>Email</th>
                        <th>Location</th>
                        <th>Age</th>
                        <th>About</th>
                        <th>Going</th>
                        <th>Applied</th>
                       
                    </tr>
                </thead>
                <tbody>
                    {rows}
                </tbody>
            </table>
        
            */  }


            </div>
        )
    }
}
export default UserCards;