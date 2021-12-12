import React, { Component } from 'react'
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';

import { withRouter } from "react-router-dom";

import "../design/RegisterForm.css";



import Login from './Login';

const emailRegex = RegExp( /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/ );


const formValid = ({ formErrors, ...rest }) => {
    let valid = true;
  
    // validate form errors being empty
    Object.values(formErrors).forEach(val => {
      val.length > 0 && (valid = false);
    });
  
    // validate the form was filled out
    Object.values(rest).forEach(val => {
      val === null && (valid = false);
    });
  
    return valid;
  };

class Registerform extends Component {

    

    constructor(props){
        super(props);
        this.state={
            email:null,
            username:null,
            password:null,
            first_name:null,
            formErrors: {
                email: "",
                username: "",
                password: "",
                first_name: ""
              }
        }

    }

    componentDidMount() {
    
    }
  
    componentWillUnmount() {
     
    }

    
    handleSubmit = e => {
        e.preventDefault();
    
        if (formValid(this.state)) {
          console.log(`
            --SUBMITTING--
            Email: ${this.state.email}
            Username: ${this.state.username}
            First Name: ${this.state.first_name}
            Password: ${this.state.password}
          `);
          this.submitForm();
          

        } else {
          console.error("FORM INVALID - DISPLAY ERROR MESSAGE");
        }
      };


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

        .then((response) => {
            if(!response.ok) {
              throw new Error(response.status);
            }  
            else { 
                this.props.history.push("./registereduser");
                return response.json();
              } 
              
          })
        .then((data) => {
            this.setState({ isLoading: false, downlines: data.response });
            console.log("DATA STORED");
          })
        .catch((error) => {
          
            console.log('error: ' + error);
            
            this.setState({ requestFailed: true });
          });


        // .then(response=>response.json())
        // .then((data)=>console.log(data));

        this.setState({
            email:'',
            username:'',
            password:'',
            first_name:''

        })

         
    }

    handleClick = () => {
        
    }



      handleChange = e => {
        e.preventDefault();
        const { name, value } = e.target;
        let formErrors = { ...this.state.formErrors };
    
        switch (name) {
          case "first_name":
            formErrors.first_name =
              value.length < 3 ? "minimum 3 characaters required" : "";
            break;
          case "username":
            formErrors.username =
                value.length < 3 ? "minimum 3 characaters required" : "";
             break;  

          case "email":
            formErrors.email = emailRegex.test(value)
              ? ""
              : "invalid email address";
            break;

          case "password":
            formErrors.password =
              value.length < 6 ? "minimum 6 characaters required" : "";
            break;

          default:
            break;
        }
    
        this.setState({ formErrors, [name]: value }, () => console.log(this.state));
      };


    render() {
        const { formErrors } = this.state;

        return (
            <div className="wrapper">
                <div className="form-wrapper">
                    <h1>Create Account</h1>
                    <form onSubmit={this.handleSubmit} noValidate>
                        
                        <div className="first_name">
                        <label htmlFor="first_name">First Name</label>
              <input
                className={formErrors.first_name.length > 0 ? "error" : null}
                placeholder="First Name"
                type="text"
                name="first_name"
                noValidate
                onChange={this.handleChange}
              />
              {formErrors.first_name.length > 0 && (
                <span className="errorMessage">{formErrors.first_name}</span>
              )}

                </div>


            <div className="username">
              <label htmlFor="username">Username</label>
              
              <input
                className={formErrors.username.length > 0 ? "error" : null}
                placeholder="UserName"
                type="text"
                name="username"
                noValidate
                onChange={this.handleChange}
              />
              {formErrors.username.length > 0 && (
                <span className="errorMessage">{formErrors.username}</span>
              )}
            </div>

            <div className="email">
              <label htmlFor="email">Email</label>
              <input
                className={formErrors.email.length > 0 ? "error" : null}
                placeholder="Email"
                type="email"
                name="email"
                noValidate
                onChange={this.handleChange}
              />
              {formErrors.email.length > 0 && (
                <span className="errorMessage">{formErrors.email}</span>
              )}
            </div>


            <div className="password">
              <label htmlFor="password">Password</label>
              <input
                className={formErrors.password.length > 0 ? "error" : null}
                placeholder="Password"
                type="password"
                name="password"
                noValidate
                onChange={this.handleChange}
              />
              {formErrors.password.length > 0 && (
                <span className="errorMessage">{formErrors.password}</span>
              )}
            </div>
            <div className="createAccount">
              <button type="submit">Create Account</button>
              <small>Already Have an Account?</small>
                <div>
                    <Link to="/login">Login</Link>
          
                     </div>
        
            </div>
          </form>
        </div>
      </div>
    );
    }
}


export default withRouter(Registerform);