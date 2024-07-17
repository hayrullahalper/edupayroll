import Div100vh from 'react-div-100vh';
import { Outlet } from 'react-router-dom';
import { Divider, Flex, ScrollArea } from '@mantine/core';

import styles from './AppLayout.module.scss';
import Sidebar from './Sidebar';

export default function AppLayout() {
	return (
		<Flex component={Div100vh}>
			<Flex p="xs" gap="xs">
				<Sidebar />
				<Divider orientation="vertical" />
			</Flex>
			<ScrollArea w="100%" h="100%" classNames={{ viewport: styles.viewport }}>
				<Outlet />
			</ScrollArea>
		</Flex>
	);
}
