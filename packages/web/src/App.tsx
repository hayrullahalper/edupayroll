import { BrowserRouter } from 'react-router-dom';
import { MantineProvider } from '@mantine/core';

import Routes from './routes/Routes';

import './globals.scss';

export default function App() {
	return (
		<MantineProvider>
			<BrowserRouter>
				<Routes />
			</BrowserRouter>
		</MantineProvider>
	);
}
