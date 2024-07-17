import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useMutation } from '@tanstack/react-query';
import { IconLockCancel } from '@tabler/icons-react';
import { Trans, useTranslation } from 'react-i18next';
import { notifications } from '@mantine/notifications';
import { Flex, Text, Paper, Stack, Divider, Alert } from '@mantine/core';

import paths from '../paths';
import { client, LoginInput } from '../../api';
import useToken from '../../contexts/token/useToken';

import LoginForm, { LoginFormInput } from './LoginForm';

export default function Login() {
	const { t } = useTranslation();
	const { setToken } = useToken();
	const [error, setError] = useState<string | null>(null);

	const login = useMutation({
		mutationFn: (loginInput: LoginInput) =>
			client('auth').login({ loginInput }),
	});

	const handleSubmit = async (input: LoginFormInput) => {
		try {
			const { node, errors } = await login.mutateAsync(input);

			if (!node?.token || !!errors.length) {
				if (errors.some(({ code }) => code === 'INVALID_CREDENTIALS')) {
					setError(t('auth.login.error.invalidCredentials'));
					return;
				}

				notifications.show({
					message: t('common.error.unknown'),
					color: 'red',
				});
				return;
			}

			setToken(node.token, input.remember);
		} catch (e) {
			notifications.show({
				message: t('common.error.unknown'),
				color: 'red',
			});
		}
	};

	return (
		<Stack gap="0.25rem">
			<Paper w={324} bg="gray.1" p="lg">
				<Stack gap="sm">
					<Stack mt="xs" gap="0" align="center">
						<img
							width={96}
							height={96}
							src="/assets/auth.png"
							alt={t('auth.login.alt')}
						/>

						<Stack px="3rem" gap="0" align="center">
							<Text fz="2rem" ff="var(--ff-title)">
								{t('auth.login.title')}
							</Text>
							<Text fz="sm" fw="200" ta="center">
								{t('auth.login.subtitle')}
							</Text>
						</Stack>
					</Stack>

					<Divider />

					{!!error && (
						<Alert color="red" variant="light" icon={<IconLockCancel />}>
							<Text fz="sm" c="red" fw="normal">
								{error}
							</Text>
						</Alert>
					)}

					<LoginForm onSubmit={handleSubmit} />
				</Stack>
			</Paper>
			<Flex justify="center" pl="xs" gap=".25rem">
				<Trans
					i18nKey="auth.login.noAccount"
					components={[
						<Text key="0" fz="xs" fw="200" />,
						<Text
							key="1"
							fz="xs"
							fw="500"
							c="indigo"
							component={Link}
							to={paths.register}
						/>,
					]}
				/>
			</Flex>
		</Stack>
	);
}
