import React from 'react';
import { shallow, mount, render } from 'enzyme';
import  {UserProfile}  from './UserProfile'


describe("<UserProfile  />", () => {
    it('UserProfile renders correctly', ()=>{
        const userprofile = shallow(<UserProfile />);
        expect(userprofile.debug()).toMatchSnapshot();
    });

})