import React from 'react';
import { shallow, mount, render } from 'enzyme';
import ConnectedUserSearch, { UserSearch } from './UserSearch';
// import * as axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import * as constant from '../../actions/constant';
import configureMockStore from 'redux-mock-store';



// jest.mock(constant.AXIOS);

describe("<UserSearch  />", () => {
    const userdata = [
        {
            "userId": 1,
            "userName": "aakash",
            "firstName": "akash",
            "lastName": "verma",
            "userEmail": "a.v2612@gmail.com",
            "dateOfBirth": "1993-12-25T18:30:00.000Z",
            "gender": "M",
            "bloodGroup": "O+",
            "weight": null,
            "height": 186,
            "patientData": null,
            "userRole": "DOCTOR"
        }
    ]

    const usersearch = shallow(<UserSearch />);
    it('renders correctly', () => {
        expect(usersearch.debug()).toMatchSnapshot();
    });

    it('handle get users success', async () => {

        var mock = new MockAdapter(constant.AXIOS);
        mock.onGet(constant.baseApiUrl + "users", {}).reply(200, userdata);
        let usersearchinstance = await usersearch.instance();
        await usersearchinstance.handleGetUsers({});
        expect(usersearchinstance.state.usersData.length).toEqual(1);
    });

    it('handle get users unauthorized', async () => {

        var mock = new MockAdapter(constant.AXIOS);
        mock.onGet(constant.baseApiUrl + "users", {}).reply(204, null);
        let usersearchinstance = await usersearch.instance();
        await usersearchinstance.handleGetUsers({});
        expect(usersearchinstance.state.usersData.length).toEqual(0);
    });

    it('handle get users unauthorized', async () => {

        var mock = new MockAdapter(constant.AXIOS);
        jest.spyOn(window, 'alert');
        mock.onGet(constant.baseApiUrl + "users", {}).reply(403, {});
        let usersearchinstance = await usersearch.instance();
        await usersearchinstance.handleGetUsers({});
        expect(usersearchinstance.state.usersData.length).toEqual(0);
    });

    it('handle get users error', async () => {

        var mock = new MockAdapter(constant.AXIOS);
        mock.onGet(constant.baseApiUrl + "users", {}).reply(500, {});
        let usersearchinstance = await usersearch.instance();
        await usersearchinstance.handleGetUsers({});
        expect(usersearchinstance.state.usersData.length).toEqual(0);
    });

    it('should call methodName during componentDidMount', () => {
        const handleGetUsersFake = jest.spyOn(UserSearch.prototype, 'handleGetUsers');
        const userSearchMount = mount(<UserSearch />);
        expect(handleGetUsersFake).toHaveBeenCalledTimes(1);
    });

    describe('test mapStateToProps', () => {
        it('test map state to props', () => {
            const mockStore = configureMockStore();
            let wrapper, store;
            const initialState = {
                "data": {
                    "user": { "id": 1, "userRole": "DOCTOR", "userName": "aakash", "token": "xxxxxasdf" }
                }
            };
            store = mockStore(initialState);
            // Shallow render the container passing in the mock store
            wrapper = shallow(
                <ConnectedUserSearch store={store} />
            );
            expect(wrapper.props().user).toEqual(initialState.user);
        })
    }
    )
})