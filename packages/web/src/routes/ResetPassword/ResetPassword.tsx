import type { ResetPasswordInput } from '../../api';
import type { ResetPasswordFormInput } from './ResetPasswordForm';
import { Box, Divider, Flex, Paper, Stack, Text } from '@mantine/core';
import { notifications } from '@mantine/notifications';
import { useMutation } from '@tanstack/react-query';
import { useState } from 'react';

import { Trans, useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import { client } from '../../api';

import paths from '../paths';
import ResetPasswordForm from './ResetPasswordForm';
import ResetPasswordSuccess from './ResetPasswordSuccess';

export default function ResetPassword() {
	const { t } = useTranslation();

	const [email, setEmail] = useState<string>();
	const [emailSent, setEmailSent] = useState(false);

	const resetPassword = useMutation({
		mutationFn: (resetPasswordInput: ResetPasswordInput) =>
			client.auth.resetPassword({ resetPasswordInput }),
	});

	const handleSubmit = async (input: ResetPasswordFormInput) => {
		try {
			const { data, errors } = await resetPassword.mutateAsync(input);

			if (!data?.success || !!errors.length) {
				notifications.show({
					message: t('common.error.unknown'),
					color: 'red',
				});
				return;
			}

			setEmail(input.email);
			setEmailSent(true);
		}
		catch (e) {
			notifications.show({
				message: t('common.error.unknown'),
				color: 'red',
			});
		}
	};

	const handleReset = () => {
		setEmailSent(false);
		setEmail(undefined);
	};

	const handleResendEmail = async () => {
		try {
			if (!email) {
				return;
			}

			const { data, errors } = await resetPassword.mutateAsync({ email });

			if (!data?.success || !!errors.length) {
				notifications.show({
					message: t('common.error.unknown'),
					color: 'red',
				});
				return;
			}

			notifications.show({
				message: t('auth.resetPassword.emailResend.content'),
				title: t('auth.resetPassword.emailResend.title'),
				color: 'green',
			});
		}
		catch (e) {
			notifications.show({
				message: t('common.error.unknown'),
				color: 'red',
			});
		}
	};

	if (emailSent) {
		return (
			<ResetPasswordSuccess
				onRequestReset={handleReset}
				onRequestResend={handleResendEmail}
			/>
		);
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
							src="/assets/reset-password.png"
							alt={t('auth.resetPassword.alt')}
						/>

						<Stack gap="md" align="center">
							<Text ta="center" lh="1" fz="2rem" ff="var(--ff-title)">
								{t('auth.resetPassword.title')}
							</Text>
							<Text fz="sm" fw="200" ta="center">
								{t('auth.resetPassword.subtitle')}
							</Text>
						</Stack>
					</Stack>

					<Divider />

					<ResetPasswordForm onSubmit={handleSubmit} />
				</Stack>
			</Paper>

			<Flex justify="center" pl="xs" gap=".25rem">
				<Text key="0" fz="xs" fw="200">
					<Trans
						i18nKey="auth.resetPassword.backToLogin"
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
