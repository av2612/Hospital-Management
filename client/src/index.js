import React from 'react';
import ReactDOM from 'react-dom';
import thunk from 'redux-thunk';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';

import App from './components/App';
import reducers from './reducers';
import './index.css';
import promise from 'redux-promise';
import { I18nextProvider } from "react-i18next";
import i18n from "./i18n";

ReactDOM.render(

    <Provider store={createStore(reducers,
        applyMiddleware(thunk, promise
        ))}>
            <I18nextProvider i18n={i18n}>
        <App i18n={i18n} />
        </I18nextProvider>
    </Provider>,
    document.getElementById('root'));