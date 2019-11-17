import React from 'react';
import { shallow, mount, render } from 'enzyme';
import { Header } from './Header';
import { NavLink } from 'react-router-dom';
// import localStorage from '../../__mocks__/localstorage';


describe("<Header  />", () => {

    const propsADMINRole = {
        "isAuthenticated": true,
        "user": { "userId": 1, "username": "aakash", "userRole": "ADMIN", "token": "YWFrYXNoOmFzZGY=" }
    };

    const propsUNKOWNRole = {
        "isAuthenticated": true,
        "user": { "userId": 1, "username": "aakash", "userRole": "UNKNOWN", "token": "YWFrYXNoOmFzZGY=" }
    };

    const propsDOCTORRole = {
        "isAuthenticated": true,
        "user": { "userId": 1, "username": "aakash", "userRole": "DOCTOR", "token": "YWFrYXNoOmFzZGY=" }
    };

    const propsPATIENTRole = {
        "isAuthenticated": true,
        "user": { "userId": 1, "username": "aakash", "userRole": "PATIENT", "token": "YWFrYXNoOmFzZGY=" }
    };

    it('Header renders correctlywithout props', () => {
        const header = shallow(<Header />);
        expect(header.debug()).toMatchSnapshot();
    });

    it('Header renders correctly with PATIENT props', () => {
        const header = shallow(<Header {...propsPATIENTRole} />);
        expect(header.debug()).toMatchSnapshot();
    });

    it('Header renders correctly with DOCTOR props', () => {
        const header = shallow(<Header {...propsDOCTORRole} />);
        expect(header.debug()).toMatchSnapshot();
    });

    it('Header renders correctly with ADMIN props', () => {
        const header = shallow(<Header {...propsADMINRole} />);
        expect(header.debug()).toMatchSnapshot();
    });

    it('Header renders correctly with UNKWOWN role props', () => {
        
        const header = shallow(<Header {...propsUNKOWNRole} />);
        expect(header.debug()).toMatchSnapshot();
    });

    it('navlink check for PATIENT', () => {
        
        let logOutCalled = 0;
        const props = {
            logOut: (()=>{logOutCalled+=1})
        };
        // let newprops = {...props,...propsADMINRole}
        localStorage.setItem('login', "")
        const HeaderMount = mount(<Header {...props} />);
        expect(logOutCalled).toEqual(1);
    });


    it('should call methodName during componentDidMount', () => {
        // global.localStorage = localStorage;
        let handlesuccesfullLoginCalled = 0;
        const props = {
            succesfullLogin: ((data, prop) => {
                handlesuccesfullLoginCalled += 1;
            }),
            logOut: (()=>{})
        };
        localStorage.clear();
        localStorage.setItem('login', JSON.stringify({ "userId": 1, "username": "aakash", "userRole": "DOCTOR", "token": "YWFrYXNoOmFzZGY=" }))
        console.log('check localstorage', localStorage.getItem('login'));
        const HeaderMount = mount(<Header {...props} />);
        expect(handlesuccesfullLoginCalled).toEqual(1);
    });

    it('should call methodName during componentDidMount', () => {
        
        let logOutCalled = 0;
        const props = {
            logOut: (()=>{logOutCalled+=1})
        };
        // let newprops = {...props,...propsADMINRole}
        localStorage.setItem('login', "")
        const HeaderMount = mount(<Header {...props} />);
        expect(logOutCalled).toEqual(1);
    });

    it("contains correct prop history register", () => {
        const header = shallow(<Header />);
        expect(header.find(NavLink).first().simulate('click'));
    });

})