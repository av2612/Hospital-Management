
import { combineReducers } from 'redux';
// import auth from './auth'


const initialState = {
  isAuthenticated: false,
  user: null
};

export const data = (state = initialState, action = {}) => {
  switch (action.type) {
    case 'LOGIN':
      if (action.loginUser && typeof (action.loginUser.token) == "string") {
        return {
          isAuthenticated: true,
          user: action.loginUser
        };
      }
      else {
        return initialState;
      }
    case 'LOGOUT':
      return initialState;
    default:
      return state;
  }
};
export default combineReducers({
  data
});
