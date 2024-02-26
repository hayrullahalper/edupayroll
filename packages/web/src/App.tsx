import { MantineProvider } from '@mantine/core';
import { BrowserRouter } from 'react-router-dom';
import Routes from './routes/Routes.tsx';

import './globals.scss';

export default function App() {
  return (
    <MantineProvider>
      <BrowserRouter>
        <Routes />
      </BrowserRouter>
    </MantineProvider>
  );
};
