import React from 'react';

import { Route, Redirect } from 'react-router-dom';
import axios from 'axios';
import { connect } from 'react-redux';


export const PrivateRoute = ({ component: Component, ...rest }) => (

    <Route {...rest} render={props => {
        let login = JSON.parse(localStorage.getItem('login'));
        if (login)
            axios.defaults.headers.common['Authorization'] = "Basic " + login.token;
        return login ? <Component {...props} />
            : <Redirect to={{ pathname: '/', state: { from: props.location } }} />

    }} />

)

function mapStatesToProps(state) {
    return {
        user: state.data.user,
    };
}
export default connect(mapStatesToProps)(PrivateRoute);