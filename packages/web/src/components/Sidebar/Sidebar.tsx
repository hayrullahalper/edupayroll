import {
	Box,
	Flex,
	Text,
	Paper,
	Stack,
	Button,
	Avatar,
	Divider,
	Tooltip,
	ActionIcon,
} from '@mantine/core';
import { IconLogout } from '@tabler/icons-react';
import { NavLink, matchRoutes, useLocation, Link } from 'react-router-dom';

import Logo from '../Logo';
import paths from '../../routes/paths';

import styles from './Sidebar.module.scss';
import { links } from './Sidebar.utils';

export default function Sidebar() {
	const location = useLocation();

	return (
		<Paper component={Stack} className={styles.container}>
			<Flex py="sm" pb="xs" justify="center" align="center">
				<Logo />
			</Flex>
			<Divider />
			<Stack gap="xs" px="xs">
				{links.map((link) => {
					let match = matchRoutes([{ path: link.to }], location);

					link.relations?.forEach((relation) => {
						match = match || matchRoutes([{ path: relation }], location);
					});

					return (
						<Button
							fw="400"
							to={link.to}
							key={link.to}
							color="indigo"
							component={NavLink}
							justify="flex-start"
							variant={match ? 'filled' : 'light'}
							leftSection={<link.icon size={18} stroke={1.5} />}
						>
							{link.label}
						</Button>
					);
				})}
			</Stack>
			<Box flex="1" />
			<Divider />
			<Flex pt="0" pb="md" px="xs" justify="space-between" align="center">
				<Flex
					w={132}
					td="none"
					to={paths.profile}
					gap="xs"
					align="center"
					justify="flex-start"
					component={Link}
				>
					<Avatar color="indigo">HY</Avatar>
					<Text fz="sm" fw="300" c="black" truncate>
						Halis Yücel
					</Text>
				</Flex>
				<Tooltip label="Çıkış yap" position="right" withArrow>
					<ActionIcon
						size="md"
						color="indigo"
						variant="light"
						component={Link}
						to={paths.logout}
					>
						<IconLogout size={18} stroke={1.5} />
					</ActionIcon>
				</Tooltip>
			</Flex>
		</Paper>
	);
}
