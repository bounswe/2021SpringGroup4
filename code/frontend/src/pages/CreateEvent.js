import React, { Component } from 'react'
 

class CreateEvent extends Component {
    constructor(){
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

        fetch('http://localhost:8000/api/event/' ,{
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
            location:'', 
            numbofplayers:''

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
                            <input value={this.state.location} name="date" onChange={this.changeHandler} type="date" className="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <th>Time</th>
                        <td>
                            <input value={this.state.location} name="time" onChange={this.changeHandler} type="time" className="form-control" />
                        </td>
                    </tr>

                    <tr>
                        <th>duration</th>
                        <td>
                            <input value={this.state.location} name="duration" onChange={this.changeHandler} type="text" className="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <th>Location</th>
                        <td>
                            <input value={this.state.location} name="location" onChange={this.changeHandler} type="text" className="form-control" />
                        </td>
                    </tr>
                    
                    <tr>
                        <th>sportType</th>
                        <td>
                            <input value={this.state.location} name="sportType" onChange={this.changeHandler} type="text" className="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <th>maxPlayers</th>
                        <td>
                            <input value={this.state.location} name="maxPlayers" onChange={this.changeHandler} type="text" className="form-control" />
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
