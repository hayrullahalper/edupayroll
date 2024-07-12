import { Outlet } from 'react-router-dom';
import { use100vh } from 'react-div-100vh';
import { Flex, ScrollArea } from '@mantine/core';
import { useLayoutEffect, useRef } from 'react';

import Logo from '../../components/Logo';

import styles from './AuthLayout.module.scss';

export default function AuthLayout() {
	const height = use100vh();
	const viewportRef = useRef<HTMLDivElement>(null);

	useLayoutEffect(() => {
		if (!!viewportRef.current) {
			const inner = viewportRef.current.querySelector('& > div');
			const isScrollable =
				viewportRef.current.scrollHeight > viewportRef.current.clientHeight;

			if (!isScrollable) {
				inner?.setAttribute('style', `height: ${height}px`);
			}
		}
	}, [height]);

	return (
		<ScrollArea h={height || '100%'} viewportRef={viewportRef}>
			<Flex
				px="sm"
				py="xl"
				gap="xl"
				align="center"
				justify="center"
				direction="column"
				className={styles.container}
			>
				<Logo />
				<Outlet />
			</Flex>
		</ScrollArea>
	);
}
