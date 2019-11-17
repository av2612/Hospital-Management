import React, { Component } from 'react';
import { connect } from 'react-redux';
import { NavLink } from 'react-router-dom';
import { logOut, succesfullLogin } from '../../actions';
import './Header.css';

export class Header extends Component {

    constructor(props) {
        super(props);
        this.state = {
            showMenu: false,
            Register: true
        };
    }

    componentDidMount() {
        let login = null;
        try {
            login = JSON.parse(localStorage.getItem('login'));

            this.props.succesfullLogin(login, this.props);
        }
        catch (error) {
            this.props.logOut(this.props);
        }
    }

    setToggle = (show=false)=>{
        document.getElementById("toggle").checked = show;
    }

    render() {
        let headerComponent = null;
        const logout = (<NavLink to='/' onClick={() => { this.setToggle(); this.props.logOut(this.props); }}>Log Out  </NavLink>)
        if (this.props.isAuthenticated === true) {
            switch (this.props.user.userRole) {
                case "PATIENT":
                    headerComponent = (
                        <div className="nav">
                            <div className="nav-wrapper">
                                <nav>
                                    {logout}
                                </nav>
                            </div>
                        </div>
                    )
                    break;
                case "DOCTOR":
                    headerComponent = (
                        <div className="nav">
                            <div className="nav-wrapper">
                                <nav>
                                    {logout}<br />
                                    <NavLink onClick = {this.setToggle} to={'/user-profile/profile/' + this.props.user.userId}>{this.props.user.username.toUpperCase()} Profile</NavLink><br />
                                    <NavLink onClick = {this.setToggle} to='/user-search'>  Patient Search </NavLink>
                                </nav>
                            </div>
                        </div>
                    )
                    break;
                case "ADMIN":
                    headerComponent = (
                        <div className="nav">
                            <div className="nav-wrapper">
                                <nav>
                                    {logout}
                                    <NavLink onClick = {this.setToggle} to={{ pathname: '/user-profile/profile/', search: '?userId=' + this.props.user.userId }}> {this.props.user.username.toUpperCase()} Profile</NavLink>
                                    <NavLink onClick = {this.setToggle} to='/user-search'>  User Search </NavLink>
                                </nav>
                            </div>
                        </div>
                    )
                    break;
                default:
                    headerComponent = (<div className="menu-drop">
                        {logout}
                    </div>);

            }
        }
        else {
            if (this.props.location.pathname === "/" || this.props.location.pathname === "/register") {
                if(this.props.location.pathname === "/" && !this.state.Register )
                    this.setState({Register: true})
                if(this.props.location.pathname === "/register" && this.state.Register)
                    this.setState({Register: false})
                headerComponent = (<div className="nav">
                    <div className="nav-wrapper">
                        <nav>
                            <NavLink  to={this.state.Register ? "/register" : "/"} onClick={() => {
                                this.setToggle(); this.setState({ "Register": !this.state.Register })
                            }}
                            > {this.state.Register ? "Register" : "Login"}</NavLink>
                        </nav>
                    </div>
                </div>);
            }
            else
                headerComponent = (<div></div>)
        }

        return (<div onMouseEnter={()=>this.setToggle(true)} onMouseLeave={()=>this.setToggle(false)}><input id="toggle" type="checkbox"></input>

            <label htmlFor="toggle" className="hamburger">
                <div className="top-bun"></div>
                <div className="meat"></div>
                <div className="bottom-bun"></div>

            </label>
            {headerComponent}
        </div>)
    }


}

const mapStateToProps = (state) => {
    return {
        isAuthenticated: state.data.isAuthenticated,
        user: state.data.user
    };
}

export default connect(mapStateToProps, { succesfullLogin, logOut })(Header)