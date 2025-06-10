import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './components/apps/App';
import { BrowserRouter } from 'react-router-dom';
import { Provider } from 'react-redux'
import { store } from './redux/store/Store';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
console.info(`REACT_APP_NAME:${process.env.REACT_APP_NAME}`);
console.info(`REACT_APP_VERSION:${process.env.REACT_APP_VERSION}`);
console.info(`REACT_APP_DOMAIN:${process.env.REACT_APP_DOMAIN}`);
console.info(`REACT_APP_WS_DOMAIN:${process.env.REACT_APP_WS_DOMAIN}`);
console.info(`REACT_APP_WS_PROTOCOL:${process.env.REACT_APP_WS_PROTOCOL}`);
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Provider store={store}>
        <App />
      </Provider>
    </BrowserRouter>
  </React.StrictMode>
);

