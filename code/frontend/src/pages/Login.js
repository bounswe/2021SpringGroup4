
import React, { Component } from 'react'


class Login extends Component {
  constructor(props){
    super(props);
    this.state={
        username:'',
        password:''

    }

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
  console.log("Data submitted.");

  fetch('http://localhost:8000/api/auth/login/',{
      method:'POST',
      body:JSON.stringify(this.state),
      headers:{
          'Content-type': 'application/json; charset=UTF-8',
      },
  })

  .then(response=>response.json())
  .then((data)=>console.log(data));

  this.setState({
      username:'',
      password:'',

  })

}





//Log out
  logOut(){
    console.log("Logout");

    this.setState({
      username:'',
      password:'',

    })

  }


  render() {
    return (
    <div>   
      <table className="table table-bordered">
                <tbody>
                    <tr>
                        <th>UserName</th>
                        <td>
                            <input value={this.state.username} name="username" onChange={this.changeHandler} type="text" className="form-control" placeholder="username" />
                        </td>
                    </tr>
                    <tr>
                        <th>Password</th>
                        <td>
                            <input value={this.state.password} name="password" onChange={this.changeHandler} type="password" className="form-control" placeholder="******" />
                        </td>
                    </tr>
                    <tr>
                        <td colSpan="2">
                            <input type="submit" value="Login" onClick={this.submitForm} className="btn btn-dark" />
                        </td>
                    </tr>
                </tbody>
            </table> 
  </div>
    )


  }

}
export default Login

