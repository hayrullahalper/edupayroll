import Div100vh from 'react-div-100vh';
import { Outlet } from 'react-router-dom';
import { Box, Divider, Flex } from '@mantine/core';

import Sidebar from './Sidebar';

export default function AppLayout() {
	return (
		<Flex component={Div100vh}>
			<Flex p="xs" gap="xs">
				<Sidebar />
				<Divider orientation="vertical" />
			</Flex>
			<Box pt="xs" pr="xs" w="100%">
				<Outlet />
			</Box>
		</Flex>
	);
}
