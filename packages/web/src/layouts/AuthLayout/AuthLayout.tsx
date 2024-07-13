import { Flex } from '@mantine/core';
import { Outlet } from 'react-router-dom';
import { use100vh } from 'react-div-100vh';

import Logo from '../../components/Logo';

import './AuthLayout.scss';

export default function AuthLayout() {
	const height = use100vh();

	return (
		<Flex
			px="sm"
			py="xl"
			gap="xl"
			align="center"
			justify="center"
			direction="column"
			mih={height ?? '100vh'}
		>
			<Logo />
			<Outlet />
		</Flex>
	);
}
