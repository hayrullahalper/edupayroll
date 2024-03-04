import { BrowserRouter } from 'react-router-dom';
import { MantineProvider } from '@mantine/core';

import light from './themes/light';
import Routes from './routes/Routes';

import './globals.scss';

export default function App() {
	return (
		<MantineProvider theme={light}>
			<BrowserRouter>
				<Routes />
			</BrowserRouter>
		</MantineProvider>
	);
}
