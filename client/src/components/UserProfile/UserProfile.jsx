import React, { Component } from 'react';
import { connect } from 'react-redux';
import axios from 'axios';
import { baseApiUrl } from '../../actions/constant';
import { logOut } from '../../actions/';
import './UserProfile.css';
import { EDD } from './EDD';


export class UserProfile extends Component {
    constructor(props) {
        super(props)
        this.state = {
            "userProfile": null,
            "personalDetailsDisabled": true

        }
        this.getUserProfileData();
    }

    getUserProfileData(props = this.props) {
        let data = null;
        if (props.match.params.userId !== "")
            data = "?userId=" + props.match.params.userId + "&userRoleType=PATIENT";
        else
            return props.history.push('/user-profile/profile');
        return new Promise((resolve) => {
            axios.get(baseApiUrl + "users" + data)
                .then(response => {
                    if (response.status === 200) {
                        this.setState({ "userProfile": response.data[0] });
                    }
                    else {
                        alert("Something went wrong");
                        props.logout();
                    }
                    resolve(response.data);
                }
                ).catch(error => {
                    if (error.response != null && error.response.status === 403) {
                        alert(error.response.data);
                        props.logOut(this.props);
                    }
                    else {
                        alert("Something went wrong. We are logging you out.");
                        props.logOut(this.props);
                    }
                });
        });
    }

    putUserProfileData = () => {
        this.setState({ userProfile: null });
        return new Promise((resolve) => {
            axios.put(baseApiUrl + "users", this.state.userProfile)
                .then(response => {
                    if (response.status === 200) {
                        this.setState({ personalDetailsDisabled: true });

                        this.setState({ userProfile: response.data });
                    }
                    else {
                        alert("Something went wrong");
                        this.props.logout();
                    }
                    resolve(response.data);
                }
                ).catch(error => {
                    if (error.response != null && error.response.status === 403) {
                        alert(error.response.data);
                        this.props.logOut(this.props);
                    }
                    else {
                        alert("Something went wrong. We are logging you out.");
                        this.props.logOut(this.props);
                    }
                });
        });
    }

    handleChange = (event) => {
        this.setState({ userProfile: { ...this.state.userProfile, [event.target.name]: event.target.value } });
    }

    submitHandler = (event) => {
        event.preventDefault();
        this.putUserProfileData();
    }

    render() {
        const { personalDetailsDisabled, userProfile } = this.state;

        return userProfile ? (
            <div>
                <hr />
                <h2>{userProfile.firstName} {userProfile.lastName}'s Profile<button onClick={(event) => { event.preventDefault(); this.setState({ "personalDetailsDisabled": !this.state.personalDetailsDisabled }) }} className="pencil-button"></button></h2>
                <hr />
                <div className="container">

                    <form onSubmit={(event) => { this.submitHandler(event) }}>
                        <div className="row">
                            <div className="col-25">
                                <label>User Name</label>
                            </div>
                            <div className="col-75">
                                <input type="text" name="userName" disabled value={userProfile.userName} readOnly />
                            </div>
                            <div className="col-25">
                                <label>User Email</label>
                            </div>
                            <div className="col-75">
                                <input type="text" name="userEmail" disabled value={userProfile.userEmail} readOnly />

                            </div>
                        </div>
                        <div className="row">
                            <div className="col-25">
                                <label>First Name</label>
                            </div>
                            <div className="col-75">
                                <input type="text" name="firstName" disabled={personalDetailsDisabled} value={userProfile.firstName} onChange={(event) => { this.handleChange(event); }} />
                            </div>
                            <div className="col-25">
                                <label>Last Name</label>
                            </div>
                            <div className="col-75">
                                <input type="text" name="lastName" disabled={personalDetailsDisabled} value={userProfile.lastName} onChange={(event) => { this.handleChange(event); }} />
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-25">
                                <label>Gender</label>
                            </div>
                            <div className="col-75">
                                <select name="gender" disabled={personalDetailsDisabled} value={userProfile.gender} onChange={(event) => { this.handleChange(event); }} >
                                    <option value="M">Male</option>
                                    <option value="F">Female</option>
                                    <option value="O">Others</option>
                                </select>
                            </div>
                            <div className="col-25">
                                <label>Date Of Birth</label>
                            </div>
                            <div className="col-75">
                                <input type="datetime-local" name="dateOfBirth" disabled={personalDetailsDisabled} value={userProfile.dateOfBirth ? userProfile.dateOfBirth.replace(" ", "T") : ""} onChange={(event) => { this.handleChange(event); }} />
                            </div>
                        </div>
                        <input type="submit" disabled={personalDetailsDisabled} value="Update" style={{ "marginRight": "10%" }}></input>
                    </form>
                    {userProfile.gender === "F" && personalDetailsDisabled ?
                        <EDD props={this.props} updateState={this.getUserProfileData} EDDData={userProfile.patientData.eddList} />
                        : null}
                </div>
            </div>
        ) : (<div className="loader"  ></div>)
    };
}


function mapStatesToProps(state) {
    return {
        user: state.data.user,
    };
}
export default connect(mapStatesToProps, { logOut })(UserProfile);