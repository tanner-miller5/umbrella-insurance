import React from 'react';
import { render, screen } from '@testing-library/react';
import App from '../components/apps/App';
import { BrowserRouter } from 'react-router-dom';
import { Provider } from 'react-redux'
import { store } from '../redux/store/Store';

test('renders learn react link', () => {
  render(
    <React.StrictMode>
    <BrowserRouter>
      <Provider store={store}>
        <App />
      </Provider>
    </BrowserRouter>
  </React.StrictMode>
  );
  //const linkElement = screen.getByText(/learn react/i);
  //expect(linkElement).toBeInTheDocument();
});
