import React, { Component } from 'react'
 import Autocomplete2 from "../components/Autocomplete2"

class CreateEvent extends Component {
    constructor(){
        const date = new Date();
        super();
        this.state={ 
            title:'',
            description:'',
            date:'',
            time:'',
            duration:'',
            location:'',
            sportType:'',
            maxPlayers:''
    

        };
    

    this.changeHandler=this.changeHandler.bind(this);
    this.submitForm=this.submitForm.bind(this);
    
    
    }

    //ınput change handler
    changeHandler(event){
        console.log("Input has been changed..");
        this.setState({
            [event.target.name]:event.target.value
        });
    }


    //submitform
    submitForm(){
        console.log("Event Data submitted.");

        fetch('http://3.67.188.187:8000/api/events/' ,{
            method:'POST',
            body:JSON.stringify(this.state),
            headers:{
                'Content-type': 'application/json; charset=UTF-8',
            },
        })

        .then(response=>response.json())
        .then((data)=>console.log(data));

        this.setState({
            title:'',
            description:'',
            date:'',
            time:'',
            duration:'',
            location:'',
            sportType:'',
            maxPlayers:''

        })

    }


    render() {
        return (
            <div>
                <h1>Create New Event</h1>
                <table className="table table-bordered">
                <tbody>
                    <tr> 
                        <th>Title</th>
                        <td>
                            <input value={this.state.title} name="title" onChange={this.changeHandler} type="text" className="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <th>Description</th>
                        <td>
                            <input value={this.state.description} name="description" onChange={this.changeHandler} type="text" className="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <th>Date</th>
                        <td>
                            <input value={this.state.date} name="date" onChange={this.changeHandler} type="date" className="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <th>Time</th>
                        <td>
                            <input value={this.state.time} name="time" onChange={this.changeHandler} type="time" className="form-control" />
                        </td>
                    </tr>

                    <tr>
                        <th>Duration</th>
                        <td>
                            <input value={this.state.duration} name="duration" onChange={this.changeHandler} type="text" className="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <th>Location</th>
                        <td>
                            <Autocomplete2/>
                        </td>
                    </tr>

                    
                    
                    <tr>
                        <th>SportType</th>
                        <td>
                            <input value={this.state.sportType} name="sportType" onChange={this.changeHandler} type="text" className="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <th>Max Players</th>
                        <td>
                            <input value={this.state.maxPlayers} name="maxPlayers" onChange={this.changeHandler} type="text" className="form-control" />
                        </td>
                    </tr>

                    <tr>
                        <td colSpan="2">
                            <input type="submit" value="Submit" onClick={this.submitForm} className="btn btn-dark" />
                        </td>
                    </tr>
                </tbody>
            </table>
                
            </div>
        )
    }
}

export default CreateEvent
