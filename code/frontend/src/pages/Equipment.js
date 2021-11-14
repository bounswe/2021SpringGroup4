import React, { Component } from 'react'



class Equipment extends Component {
    

    constructor(){
        super();
        this.state={ 
            title:'',
            description:'',
            location:''


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
        console.log("Equipment Data submitted.");

        fetch('http://localhost:8000/api/equipment/' ,{
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
            location:''

        })

    }
    


    render() {
        return (
            <div>
                <h1>Create New Equipment</h1>
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

export default Equipment
