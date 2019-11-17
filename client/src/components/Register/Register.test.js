import React from 'react';
import { shallow, mount, render } from 'enzyme';
import  {Register}  from './Register'


describe("<Register  />", () => {
    it('Register renders correctly', ()=>{
        const register = shallow(<Register />);
        expect(register.debug()).toMatchSnapshot();
    });

})