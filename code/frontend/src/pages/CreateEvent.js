import React, { Component } from 'react'
 

class CreateEvent extends Component {
    constructor(){
        super();
        this.state={ 
            title:'',
            description:'',
            location:'',
            sportType:'',
            numberOfPlayers:'',
            date:'',
            equipments:''


        };
    

    this.changeHandler=this.changeHandler.bind(this);
    this.submitForm=this.submitForm.bind(this);
    
    }


    changeHandler(event){
        console.log("Input has been changed..");
        this.setState({
            [event.target.name]:event.target.value
        });
    }


    //submitform
    submitForm(){
        console.log("New Event has been created.");

        fetch('http://localhost:8000/api/CreateEvent/' ,{
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
            sportType:'',
            numberOfPlayers:'',
            date:'',
            equipments:''

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
                        <th>Location</th>
                        <td>
                            <input value={this.state.location} name="location" onChange={this.changeHandler} type="text" className="form-control" />
                        </td>
                    </tr>
                    <tr> 
                        <th>Title</th>
                        <td>
                            <input value={this.state.sportType} name="sportType" onChange={this.changeHandler} type="text" className="form-control" />
                        </td>
                    </tr>

                    <tr> 
                        <th>Title</th>
                        <td>
                            <input value={this.state.numberOfPlayers} name="numberOfPlayers" onChange={this.changeHandler} type="text" className="form-control" />
                        </td>
                    </tr>
                    <tr> 
                        <th>Title</th>
                        <td>
                            <input value={this.state.date} name="date" onChange={this.changeHandler} type="text" className="form-control" />
                        </td>
                    </tr>

                    <tr> 
                        <th>Title</th>
                        <td>
                            <input value={this.state.equipments} name="equipments" onChange={this.changeHandler} type="text" className="form-control" />
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
