import { Fragment } from 'react';
import { useTranslation } from 'react-i18next';
import { Button, Divider, Flex, Paper, Stack, Text } from '@mantine/core';
import { matchRoutes, NavLink, Outlet, useLocation } from 'react-router-dom';

import Header from '../../components/Header';

import { links } from './SettingsLayout.utils';

export default function SettingsLayout() {
	const { t } = useTranslation();
	const location = useLocation();

	const { label, description } = links.find(
		({ to }) => !!matchRoutes([{ path: to }], location.pathname)?.length,
	)!;

	return (
		<Stack gap="xs" h="100%" py="xs" pr="xs">
			<Header title={t('layout.settings.title')} />
			<Paper p="md" h="100%">
				<Stack gap="xs" h="100%">
					<Flex gap="xs">
						{links.map(({ to, label: content }, index) => {
							const match = !!matchRoutes([{ path: to }], location.pathname)
								?.length;

							return (
								<Fragment key={to}>
									{links.length - 1 === index && (
										<Divider orientation="vertical" />
									)}

									<Button
										to={to}
										fw="400"
										size="sm"
										component={NavLink}
										variant={match ? 'filled' : 'light'}
									>
										{content}
									</Button>
								</Fragment>
							);
						})}
					</Flex>

					<Divider />

					<Flex direction="column" h="100%" w="min(100%, 24rem)">
						<Stack gap="xs">
							<Stack gap="xs">
								<Stack gap={0}>
									<Text fz="1.5rem" fw="200" ff="var(--ff-title)">
										{label}
									</Text>
									<Text fz="sm" fw="200">
										{description}
									</Text>
								</Stack>
								<Divider />
							</Stack>

							<Outlet />
						</Stack>
					</Flex>
				</Stack>
			</Paper>
		</Stack>
	);
}
