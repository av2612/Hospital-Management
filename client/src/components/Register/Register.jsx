import React, { Component } from 'react';
import { connect } from 'react-redux';
import '../../auth.css';
import * as constants from '../../actions/constant';
import { succesfullLogin } from '../../actions/auth';

export class Register extends Component {
  constructor(props) {
    super(props);
    this.state = {
      "firstName": '',
      "lastName": '',
      "username": '',
      "email": '',
      "phoneNumber": '',
      "password": '',
      "gender": 'M'
    }
  }

  handleChange = (event) => {
    this.setState({ [event.target.name]: event.target.value })
  }

  registrationSubmit = (event) => {
    event.preventDefault();
    return new Promise((resolve) => {
      constants.AXIOS.post(constants.baseApiUrl + 'register',
        this.state).then(response => {
          if (response.status === 200) {
            this.props.succesfullLogin(response.data, this.props);
          }
          else {

            alert(response.data);
          }
          resolve(response.data);
        }
        ).catch(error => {
          alert(error.response.data);
          console.log(error);
         resolve(error);


        });
    });

  }

  render() {
    return (
      <div className="form">
        this.props
        <form onSubmit={(event) => this.registrationSubmit(event)}>
          <select name="gender" onChange={(event) => { this.handleChange(event); }}>
            <option value="M">Mr</option>
            <option value="F">Ms</option>
            <option value="F">Mrs</option>
          </select>
          <input type="text" name="firstName" placeholder="First Name" required 
          pattern = "[A-Za-z]+"
          onChange={(event) => { this.handleChange(event); }} />
          <input type="text" name="lastName" placeholder="Last Name" required onChange={(event) => { this.handleChange(event); }} />
          <input type="text" name="userName" placeholder="User Name" required onChange={(event) => { this.handleChange(event); }} />
          <input type="text" name="userEmail" placeholder="Email Address" required onChange={(event) => { this.handleChange(event); }} />
          <input type="text" name="phoneNumber" placeholder="Phone Number" required onChange={(event) => { this.handleChange(event); }} />
          <input type="password" name="password" placeholder="Password" 
          pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" 
          title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
          onChange={(event) => { this.handleChange(event); }} required  />
          <input type="submit" value="Register" />
        </form>
      </div>
    );
  }
}

export default connect(null, { succesfullLogin })(Register);