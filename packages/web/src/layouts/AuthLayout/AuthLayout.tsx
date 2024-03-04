import { Flex } from '@mantine/core';
import { Outlet } from 'react-router-dom';

import Logo from '../../components/Logo';

import styles from './AuthLayout.module.scss';

export default function AuthLayout() {
	return (
		<Flex
			gap="xl"
			align="center"
			justify="center"
			direction="column"
			className={styles.container}
		>
			<Logo />
			<Outlet />
		</Flex>
	);
}
