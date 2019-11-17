import React from 'react';
import { shallow, mount, render } from 'enzyme';
import * as authactions from './auth'
import MockAdapter from 'axios-mock-adapter';
import * as constant from './constant';
import configureMockStore from 'redux-mock-store';


it('handle login success', async () => {
    const mockStore = configureMockStore();
    let store;
    // const initialState = {
    //     isAuthenticated: false,
    //     user: null
    // };
    store = mockStore({});
    var mock = new MockAdapter(constant.AXIOS);
    let data = { "userName": "aakash", "password": "asdfg" };
    let loginUser = {
        "userId": 1,
        "username": "aakash",
        "userRole": "DOCTOR",
        "token": "YWFrYXNoOmFzZGY="
    }
    let props = { "history": { "push": (d) => { } } }
    const expectedAction = [{ type: "LOGIN", loginUser }];
    // let act;
    mock.onPost(constant.baseApiUrl + "login", data).reply(200, loginUser);
    store.dispatch(await authactions.loginHandler(data, props))
    let act = store.getActions();
    expect(act).toEqual(expectedAction)
})

it('handle login success for PATIENT', async () => {
    const mockStore = configureMockStore();
    let store;
    // const initialState = {
    //     isAuthenticated: false,
    //     user: null
    // };
    store = mockStore({});
    var mock = new MockAdapter(constant.AXIOS);
    let data = { "userName": "aakash", "password": "asdfg" };
    let loginUser = {
        "userId": 1,
        "username": "aakash",
        "userRole": "PATIENT",
        "token": "YWFrYXNoOmFzZGY="
    }
    let props = { "history": { "push": (d) => { } } }
    const expectedAction = [{ type: "LOGIN", loginUser }];
    // let act;
    mock.onPost(constant.baseApiUrl + "login", data).reply(200, loginUser);
    store.dispatch(await authactions.loginHandler(data, props))
    let act = store.getActions();
    expect(act).toEqual(expectedAction)
})

it('handle login unsuccesfull without error', async () => {
    const mockStore = configureMockStore();
    jest.spyOn(window, 'alert');
    let store;
    // const initialState = {
    //     isAuthenticated: false,
    //     user: null
    // };
    store = mockStore({});
    var mock = new MockAdapter(constant.AXIOS);
    let data = { "userName": "aakash", "password": "asdfg" };
    let loginUser = {}
    let props = { "history": { "push": (d) => { } } }
    const expectedAction = [{ type: "LOGIN", loginUser }];
    // let act;

    mock.onPost(constant.baseApiUrl + "login", data).reply(204, {});
    store.dispatch(await authactions.loginHandler(data, props))
    let act = store.getActions();
    expect(act).toEqual(expectedAction)
}
)
it('handle login unsuccesfull without error', async () => {
    const mockStore = configureMockStore();
    window.alert = jest.fn();
    let store;
    // const initialState = {
    //     isAuthenticated: false,
    //     user: null
    // };
    store = mockStore({});
    var mock = new MockAdapter(constant.AXIOS);
    let data = { "userName": "aakash", "password": "asdfg" };
    let loginUser = {}
    let props = { "history": { "push": (d) => { } } }
    const expectedAction = [{ type: "LOGIN", loginUser }];
    // let act;

    mock.onPost(constant.baseApiUrl + "login", data).reply(403, {});
    store.dispatch(await authactions.loginHandler(data, props))
    let act = store.getActions();
    expect(act).toEqual(expectedAction)
}
);


describe("auth  actions", () => {

    const loginUser = {
        "userId": 1,
        "username": "aakash",
        "userRole": "DOCTOR",
        "token": "YWFrYXNoOmFzZGY="
    }

    const expectedAction = { type: "LOGIN", loginUser };
    it('check login action', () => {
        expect(authactions.login(loginUser)).toEqual(expectedAction);
    });

})

describe("check logout", () => {

    const expectedAction = { type: "LOGOUT" };
    it('check logout action', () => {
        expect(authactions.logOut()).toEqual(expectedAction);
    });

})