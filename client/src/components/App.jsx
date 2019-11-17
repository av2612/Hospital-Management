import React, { Component } from 'react';

import Login from './Login';
import { BrowserRouter as Router } from 'react-router-dom';
import { Route } from 'react-router-dom';
import Register from './Register/Register';
import UserSearch from './UserSearch/UserSearch';
import UserProfile from './UserProfile/UserProfile';
import Header from './Header';
import { PrivateRoute } from './PrivateRoute';

class App extends Component {

    constructor(props) {
        super(props)
        this.state = {
            value: this.props.i18n.language
        };
    }

    handleChange = event => {
        let newlang = event.target.value;
        this.setState({ value: newlang });
        this.props.i18n.changeLanguage(newlang);
    };

    render() {
        return (
            <>
                <select style={{ "width": "auto", float: "left" }} value={this.state.value} onChange={(event) => this.handleChange(event)}>
                    <option value="en">English</option>
                    <option value="jap">Japanese</option>
                </select>
                <h2>{this.props.i18n.t("EDDPortal")}</h2>
                <Router >
                    <Route component={Header}></Route>

                    <div>
                        <Route path='/' exact component={(props) => <Login {...props} i18n={this.props.i18n} />} />
                        <Route path='/register' exact component={(props) => <Register {...props} i18n={this.props.i18n} />} />
                        <PrivateRoute path='/user-search' exact component={(props) => <UserSearch {...props} i18n={this.props.i18n} />} />
                        <PrivateRoute path='/user-profile/profile/:userId' exact component={(props) => <UserProfile {...props} i18n={this.props.i18n} />} />
                        <PrivateRoute path='/user-profile/:userId' exact component={(props) => <UserProfile {...props} i18n={this.props.i18n} />} />
                    </div>
                </Router>
            </>
        );
    };
}


export default (App);