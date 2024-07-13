import { useState } from 'react';
import { useMutation } from '@tanstack/react-query';
import { Trans, useTranslation } from 'react-i18next';
import { notifications } from '@mantine/notifications';
import { Link, Navigate, useSearchParams } from 'react-router-dom';
import { Box, Divider, Flex, Paper, Stack, Text } from '@mantine/core';

import paths from '../paths';
import { client, ResetPasswordCompleteInput } from '../../api';

import ResetPasswordCompleteForm, {
	ResetPasswordCompleteFormInput,
} from './ResetPasswordCompleteForm';
import ResetPasswordCompleteSuccess from './ResetPasswordCompleteSuccess';

export default function ResetPasswordComplete() {
	const { t } = useTranslation();

	const [searchParams] = useSearchParams();
	const [success, setSuccess] = useState(false);

	const token = searchParams.get('token');

	const resetPasswordComplete = useMutation({
		mutationFn: (resetPasswordCompleteInput: ResetPasswordCompleteInput) =>
			client('auth').resetPasswordComplete({ resetPasswordCompleteInput }),
	});

	const handleSubmit = async (input: ResetPasswordCompleteFormInput) => {
		try {
			if (!token) {
				return;
			}

			const { data, errors } = await resetPasswordComplete.mutateAsync({
				token,
				...input,
			});

			if (!data || !!errors.length) {
				notifications.show({
					message: t('common.error.unknown'),
					color: 'red',
				});
				return;
			}

			setSuccess(true);
		} catch (e) {
			notifications.show({
				message: t('common.error.unknown'),
				color: 'red',
			});
		}
	};

	if (success) {
		return <ResetPasswordCompleteSuccess />;
	}

	if (!token?.trim()) {
		return <Navigate replace to={paths.resetPassword} />;
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
							src="/assets/reset-password-complete.png"
							alt={t('auth.resetPasswordComplete.alt')}
						/>

						<Stack gap="md" align="center">
							<Text ta="center" lh="1" fz="2rem" ff="var(--ff-title)">
								{t('auth.resetPasswordComplete.title')}
							</Text>
							<Text fz="sm" fw="200" ta="center">
								{t('auth.resetPasswordComplete.subtitle')}
							</Text>
						</Stack>
					</Stack>

					<Divider />

					<ResetPasswordCompleteForm
						onSubmit={handleSubmit}
						loading={resetPasswordComplete.isPending}
					/>
				</Stack>
			</Paper>

			<Flex justify="center" pl="xs" gap=".25rem">
				<Text key="0" fz="xs" fw="200">
					<Trans
						i18nKey="auth.resetPasswordComplete.backToLogin"
						components={[
							<Text
								key="0"
								fz="xs"
								fw="500"
								c="indigo"
								to={paths.login}
								component={Link}
							/>,
						]}
					/>
				</Text>
			</Flex>
		</Stack>
	);
}
