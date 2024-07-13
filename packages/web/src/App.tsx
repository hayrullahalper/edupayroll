import { MantineProvider } from '@mantine/core';
import { BrowserRouter } from 'react-router-dom';
import { Notifications } from '@mantine/notifications';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

import light from './themes/light';
import Routes from './routes/Routes';
import { UserProvider } from './contexts/user';
import { TokenProvider } from './contexts/token';

import './locales';
import './globals.scss';

const queryClient = new QueryClient({
	defaultOptions: {
		queries: {
			refetchOnWindowFocus: false,
		},
	},
});

export default function App() {
	return (
		<QueryClientProvider client={queryClient}>
			<MantineProvider theme={light}>
				<TokenProvider>
					<UserProvider>
						<BrowserRouter>
							<Routes />
						</BrowserRouter>
					</UserProvider>
				</TokenProvider>

				<Notifications position="top-right" />
			</MantineProvider>
		</QueryClientProvider>
	);
}
