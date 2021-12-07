

import React, { Component } from 'react'

class Register extends Component {
    constructor(){
        super();
        this.state={
            email:'',
            username:'',
            first_name:'',
            password:''

        }
    
    this.changeHandler=this.changeHandler.bind(this);
    this.submitForm=this.submitForm.bind(this);
    
    }

    componentWillUnmount(){
        
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
        console.log("Data submitted.");

        fetch('http://3.67.188.187:8000/api/auth/register/',{
            method:'POST',
            body:JSON.stringify(this.state),
            headers:{
                'Content-type': 'application/json; charset=UTF-8',
            },
        })

        .then(response=>response.json())
        .then((data)=>console.log(data));

        this.setState({
            email:'',
            username:'',
            first_name:'',
            password:''
            

        })

    }

    render() {
        return (
            <table className="table table-bordered">
                <tbody>
                    <tr>
                        <th>E-Mail</th>
                        <td>
                            <input value={this.state.email} name="email" onChange={this.changeHandler} type="text" className="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <th>UserName</th>
                        <td>
                            <input value={this.state.username} name="username" onChange={this.changeHandler} type="text" className="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <th>Password</th>
                        <td>
                            <input value={this.state.password} name="password" onChange={this.changeHandler} type="text" className="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <th>First Name</th>
                        <td>
                            <input value={this.state.first_name} name="first_name" onChange={this.changeHandler} type="text" className="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <td colSpan="2">
                            <input type="submit" value="Submit" onClick={this.submitForm} className="btn btn-dark" />
                        </td>
                    </tr>
                </tbody>
            </table>
        );
    }
}

export default Register

