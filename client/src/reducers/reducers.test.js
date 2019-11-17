import * as reducer from './index'

describe("check reducer state for login", () => {
    it('check state for login', () => {
        let action = {
            "type": "LOGIN",
            "loginUser": { "userId": 1, "username": "aakash", "userRole": "ADMIN", "token": "YWFrYXNoOmFzZGY=" }
        }
        let expectedState = {
            isAuthenticated: true,
            user: { "userId": 1, "username": "aakash", "userRole": "ADMIN", "token": "YWFrYXNoOmFzZGY=" }
        }

        let state = reducer.data(null, action)
        expect(state).toEqual(expectedState)
    });

    it('check state for login', () => {
        let action = {
            "type": "LOGIN",
            "loginUser": { "userId": 1, "username": "aakash", "userRole": "ADMIN", "token": "YWFrYXNoOmFzZGY=" }
        }
        let expectedState = {
            isAuthenticated: true,
            user: { "userId": 1, "username": "aakash", "userRole": "ADMIN", "token": "YWFrYXNoOmFzZGY=" }
        }

        let state = reducer.data(null, action)
        expect(state).toEqual(expectedState)
    });
    
    it('check state for LOGOUT', () => {
        let action = {
            "type": "LOGOUT",
            "loginUser": {}
        }
        let expectedState = {
            isAuthenticated: false,
            user: null
        }

        let state = reducer.data(null, action)
        expect(state).toEqual(expectedState)
    });

    it('check state for LOGIN in case of no data', () => {
        let action = {
            "type": "LOGIN",
            "loginUser": {}
        }
        let expectedState = {
            isAuthenticated: false,
            user: null
        }

        let state = reducer.data(null, action)
        expect(state).toEqual(expectedState)
    });
    it('check state without action', () => {
        
        let state = {
            isAuthenticated: true,
            user: { "userId": 1, "username": "aakash", "userRole": "ADMIN", "token": "YWFrYXNoOmFzZGY=" }
        }
        let expectedState = {
            isAuthenticated: true,
            user: { "userId": 1, "username": "aakash", "userRole": "ADMIN", "token": "YWFrYXNoOmFzZGY=" }
        }

        let stateRecieved = reducer.data(state)
        expect(stateRecieved).toEqual(expectedState)
    });
})