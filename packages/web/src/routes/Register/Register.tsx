import type { FormikHelpers } from 'formik';
import type { RegisterInput } from '../../api';
import type { RegisterFormInput } from './RegisterForm';
import { Divider, Flex, Paper, Stack, Text } from '@mantine/core';
import { notifications } from '@mantine/notifications';
import { useMutation } from '@tanstack/react-query';
import { useState } from 'react';

import { Trans, useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import { client } from '../../api';

import paths from '../paths';
import RegisterForm from './RegisterForm';
import RegisterSuccess from './RegisterSuccess';

export default function Register() {
	const { t } = useTranslation();
	const [email, setEmail] = useState<string>();
	const [emailSent, setEmailSent] = useState(false);

	const register = useMutation({
		mutationFn: (registerInput: RegisterInput) =>
			client('auth').register({ registerInput }),
	});

	const handleSubmit = async (
		input: RegisterFormInput,
		helpers: FormikHelpers<RegisterFormInput>,
	) => {
		try {
			const { node, errors } = await register.mutateAsync(input);

			if (!node || !!errors.length) {
				if (errors.some(({ code }) => code === 'USER_ALREADY_REGISTERED')) {
					helpers.setFieldError('email', t('auth.register.error.emailExists'));
					return;
				}

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

			const { node, errors } = await register.mutateAsync({ email });

			if (!node?.success || !!errors.length) {
				notifications.show({
					message: t('common.error.unknown'),
					color: 'red',
				});
				return;
			}

			notifications.show({
				message: t('auth.register.emailResend.content'),
				title: t('auth.register.emailResend.title'),
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
			<RegisterSuccess
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
						<img
							width={160}
							height={160}
							src="/assets/register.png"
							alt={t('auth.register.alt')}
						/>

						<Stack gap="md" align="center">
							<Text ta="center" lh="1" fz="2rem" ff="var(--ff-title)">
								{t('auth.register.title')}
							</Text>
							<Text fz="sm" fw="200" ta="center">
								{t('auth.register.subtitle')}
							</Text>
						</Stack>
					</Stack>

					<Divider />

					<RegisterForm onSubmit={handleSubmit} />
				</Stack>
			</Paper>

			<Flex justify="center" pl="xs" gap=".25rem">
				<Trans
					i18nKey="auth.register.alreadyAccount"
					components={[
						<Text key="0" fz="xs" fw="200" />,
						<Text
							key="1"
							fz="xs"
							fw="500"
							c="indigo"
							to={paths.login}
							component={Link}
						/>,
					]}
				/>
			</Flex>
		</Stack>
	);
}
