import axios from 'axios'
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { baseApiUrl } from '../../actions/constant';
import { NavLink } from 'react-router-dom';
import './UserSearch.css';

export class UserSearch extends Component {
    constructor(props) {
        super(props);
        this.state = { user: {}, usersData: [] };
        this.handleGetUsers({});
    }


    handleGetUsers = (data) => {
        return new Promise((resolve) => {
            axios.get(baseApiUrl + "users", data)
                .then(response => {
                    if (response.status === 200) {
                        this.setState({ usersData: response.data });
                    }
                    else{
                        this.setState({ usersData: [] });
                        // alert("No Data Found");
                    }
                    resolve(response.data);
                }
                ).catch(error => {
                    if (error.response.status === 403) {
                        this.setState({ usersData: [] });
                        resolve(error.response);
                    }
                    this.setState({ usersData: [] });
                    resolve(error);


                });
        });
    }

    render() {
        return (
            <div>
                <h2>Patient Search</h2>
                {this.state.usersData?
                <>
                <input type="text"/>
                <table className="responstable">
                    <tbody>
                        <tr>
                            <th>User Name</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>User Role</th>
                        </tr>
                        {this.state.usersData.map((userData) => {
                            return (
                                <tr key={userData["userId"]}>
                                    <td ><NavLink to={'/user-profile/' + userData["userId"] }>
                                        {userData["userName"]}</NavLink></td>
                                    <td >{userData["firstName"]}</td>
                                    <td>{userData["lastName"]}</td>
                                    <td>{userData.userRole}</td>
                                </tr>);
                        })}
                    </tbody>
                </table>
                </>
                :<div className="loader"/>
                }
            </div >
        );
    }

}

function mapStatesToProps(state){
    return {
        user: state.data.user,
    };
}

export default connect(mapStatesToProps)(UserSearch);