import axios from 'axios';
import * as constant from './constant';

export const login = (data) => {
    return {
        type: "LOGIN",
        loginUser: data
    };
}

export const loginHandler = (data, props) => {
    return new Promise((dispatch) => {
        axios.post(constant.baseApiUrl + "login", data)
            .then(response => {
                console.log(response);
                if (response.status === 200) {
                    dispatch(succesfullLogin(response.data, props));
                }
                else {
                    alert("Username or password is incorrect");
                    this.props.history.push('/');

                }
                dispatch(login({}));
            }
            )
            .catch(error => {
                console.log(error)
                alert("Username or password is incorrect");
                dispatch(login({}));
            })
    })
}

export const succesfullLogin = (data, props) => {
    localStorage.setItem('login', JSON.stringify(data));
    if (props.location.pathname === "/" || props.location.pathname === "/register")
    {
        if (data.userRole === "PATIENT")
            props.history.push('/user-profile/' + data.userId);
        else
            props.history.push('/user-search');
    }
    else
        props.history.push(props.location.pathname)
    return login(data);
}

export const logOut = (props) => {
    // console.log(props);
    delete axios.defaults.headers.common["Authorization"];
    localStorage.removeItem('login');
    localStorage.removeItem('Authorization');
    props.history.push('/');
    return {
        type: "LOGOUT"
    }
}

