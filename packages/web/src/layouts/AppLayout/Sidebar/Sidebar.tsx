import {
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
import { useTranslation } from 'react-i18next';
import { IconLogout } from '@tabler/icons-react';
import { NavLink, matchRoutes, useLocation, Link } from 'react-router-dom';

import paths from '../../../routes/paths';
import Logo from '../../../components/Logo';
import { useUser } from '../../../contexts/user';

import { links } from './Sidebar.utils';

export default function Sidebar() {
	const { t } = useTranslation();

	const user = useUser();
	const location = useLocation();

	return (
		<Paper component={Stack} w="12rem" h="100%">
			<Flex py="sm" pb="xs" justify="center" align="center">
				<Logo />
			</Flex>

			<Divider />

			<Stack gap="xs" px="xs" flex="1">
				{links.map(({ to, label, relations, icon: Icon }) => {
					const match = !!matchRoutes(
						[
							{ path: to },
							...(relations?.map((relation) => ({ path: relation })) ?? []),
						],
						location,
					)?.length;

					return (
						<Button
							fw="400"
							to={to}
							key={to}
							color="indigo"
							component={NavLink}
							justify="flex-start"
							variant={match ? 'filled' : 'light'}
							leftSection={<Icon size={18} stroke={1.5} />}
						>
							{label}
						</Button>
					);
				})}
			</Stack>

			<Divider />

			<Flex pt="0" pb="md" px="xs" justify="space-between" align="center">
				<Flex
					w={132}
					gap="xs"
					td="none"
					align="center"
					component={Link}
					to={paths.settings}
					justify="flex-start"
				>
					<Avatar color="indigo">
						{user.firstName[0].toUpperCase() + user.lastName[0].toUpperCase()}
					</Avatar>
					<Text fz="sm" fw="300" c="black" truncate>
						{user.firstName} {user.lastName}
					</Text>
				</Flex>

				<Tooltip position="right" withArrow label={t('layout.sidebar.logout')}>
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
