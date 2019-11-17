import React from 'react';
import { shallow, mount, render } from 'enzyme';
import  App  from './App'


describe("<App  />", () => {
    it('app renders correctly', ()=>{
        const app = shallow(<App />);
        expect(app.debug()).toMatchSnapshot();
    });

})