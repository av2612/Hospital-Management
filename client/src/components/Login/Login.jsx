import React, { Component } from 'react';
import { connect } from 'react-redux';

import { loginHandler } from '../../actions/index';
import '../../auth.css';
// import { NavLink } from 'react-router-dom';

export class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            'userName': '',
            'password': '',
            'showPassword': false
        }
    }
    render() {
        return (

            <div className="login-form">
                <form className="form" onSubmit={(event) => { event.preventDefault(); this.props.loginHandler(this.state, this.props) }} >
                    <div className="login-input">
                        <input className="login-username" type="text" placeholder={this.props.i18n.t("username")} minLength="5"
                            required
                            ref="username" onChange={(event) => { this.setState({ userName: event.target.value }) }} />
                    </div>
                    <div className="login-input">
                        <input className="login-password" type={this.state.showPassword?"text": "password"} placeholder="password" minLength="4"
                            required
                            ref="password" onChange={(event) => { this.setState({ password: event.target.value }) }} />
                        <i className={this.state.showPassword?"fa fa-eye-slash": "fa fa-eye"} onClick= {()=>this.setState({showPassword:!this.state.showPassword})}></i>
                    </div>
                    <input className="login-button" type="submit" value="LOGIN" />
                </form>
            </div>
        );
    }

}

export default connect(null, { loginHandler })(Login);