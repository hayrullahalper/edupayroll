import { Outlet } from 'react-router-dom';
import { Box, Divider, Flex, ScrollArea } from '@mantine/core';

import Sidebar from '../../components/Sidebar';

import styles from './AppLayout.module.scss';

export default function AppLayout() {
	return (
		<Flex className={styles.container}>
			<Flex p="xs" gap="xs">
				<Sidebar />
				<Divider orientation="vertical" />
			</Flex>
			<ScrollArea className={styles.content}>
				<Box pt="xs" pr="xs">
					<Outlet />
				</Box>
			</ScrollArea>
		</Flex>
	);
}
