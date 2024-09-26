import type { RegisterCompleteInput } from '../../api';
import type {
	RegisterCompleteFormInput,
} from './RegisterCompleteForm';
import { Box, Divider, Flex, Paper, Stack, Text } from '@mantine/core';
import { notifications } from '@mantine/notifications';
import { useMutation } from '@tanstack/react-query';

import { Trans, useTranslation } from 'react-i18next';
import { Link, Navigate, useSearchParams } from 'react-router-dom';
import { client } from '../../api';
import { useToken } from '../../contexts/token';

import paths from '../paths';
import RegisterCompleteForm from './RegisterCompleteForm';

export default function RegisterComplete() {
	const { t } = useTranslation();
	const { setToken } = useToken();
	const [searchParams] = useSearchParams();

	const token = searchParams.get('token');

	const registerComplete = useMutation({
		mutationFn: (registerCompleteInput: RegisterCompleteInput) =>
			client.auth.registerComplete({ registerCompleteInput }),
	});

	const handleSubmit = async (input: RegisterCompleteFormInput) => {
		try {
			if (!token) {
				return;
			}

			const { data, errors } = await registerComplete.mutateAsync({
				token,
				...input,
			});

			if (!data?.token || !!errors.length) {
				notifications.show({
					message: t('common.error.unknown'),
					color: 'red',
				});
				return;
			}

			setToken(data.token, true);
		}
		catch (e) {
			notifications.show({
				message: t('common.error.unknown'),
				color: 'red',
			});
		}
	};

	if (!token?.trim()) {
		return <Navigate replace to={paths.register} />;
	}

	return (
		<Stack gap="0.25rem">
			<Paper w={324} bg="gray.1" p="lg">
				<Stack gap="md">
					<Stack gap="0" align="center">
						<Box
							mt="-1rem"
							width={160}
							height={160}
							component="img"
							src="/assets/register-complete.png"
							alt={t('auth.registerComplete.alt')}
						/>

						<Stack gap="md" align="center">
							<Text ta="center" lh="1" fz="2rem" ff="var(--ff-title)">
								{t('auth.registerComplete.title')}
							</Text>
							<Text fz="sm" fw="200" ta="center">
								{t('auth.registerComplete.subtitle')}
							</Text>
						</Stack>
					</Stack>

					<Divider />

					<RegisterCompleteForm onSubmit={handleSubmit} />
				</Stack>
			</Paper>

			<Flex justify="center" pl="xs" gap=".25rem" w={324}>
				<Text fz="xs" fw="200" ta="center" px="lg">
					<Trans
						i18nKey="auth.registerComplete.acceptTerms"
						components={[
							<Text key="0" fz="xs" fw="500" c="black" component="span" />,
							<Text
								key="1"
								fz="xs"
								fw="500"
								c="indigo"
								target="_blank"
								component={Link}
								to={paths.termsOfService}
							/>,
							<Text
								key="2"
								fz="xs"
								fw="500"
								c="indigo"
								target="_blank"
								component={Link}
								to={paths.privacyPolicy}
							/>,
						]}
					/>
				</Text>
			</Flex>
		</Stack>
	);
}
