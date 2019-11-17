import React from 'react';
import { shallow, mount, render } from 'enzyme';
import { Login } from './Login'
import { NavLink } from 'react-router-dom';

describe("<Login  />", () => {
    let loginHandllerClicked = 0;
    const props = {
        loginHandler: ((data, prop) => {
            loginHandllerClicked += 1;
        })
    };
    const login = shallow(<Login {...props} />);

    beforeEach(() => {

    })

    it('renders correctly', () => {
        expect(login.debug()).toMatchSnapshot();
    });

    it('check login button clicked', () => {
        login.find('.login-button').simulate('click');
        expect(loginHandllerClicked).toEqual(1);
    })

    it('check username state set', ()=>{
        login.find('.login-username').simulate('change',{target:{ value: "aakash"}});
        expect(login.state().userName).toEqual("aakash");
    })

    it('check username state set', ()=>{
        login.find('.login-password').simulate('change',{target:{ value: "asdf"}});
        expect(login.state().password).toEqual("asdf");
    })

    it("contains correct prop history register", () => {
        expect(login.find(NavLink).first().props().to).toEqual('/register');
    });
});