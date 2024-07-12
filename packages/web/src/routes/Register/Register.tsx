import { useState } from 'react';
import { FormikHelpers } from 'formik';
import { Link } from 'react-router-dom';
import { useMutation } from '@tanstack/react-query';
import { notifications } from '@mantine/notifications';
import { Divider, Flex, Paper, Stack, Text } from '@mantine/core';

import paths from '../paths';
import { client, RegisterInput } from '../../api';

import RegisterSuccess from './RegisterSuccess';
import RegisterForm, { RegisterFormInput } from './RegisterForm';

export default function Register() {
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
			const { data, errors } = await register.mutateAsync(input);

			if (!data || !!errors.length) {
				if (errors.some(({ code }) => code === 'USER_ALREADY_REGISTERED')) {
					helpers.setFieldError(
						'email',
						'Bu e-posta adresi zaten kullanılıyor. Lütfen farklı bir e-posta adresi deneyin.',
					);
					return;
				}

				notifications.show({
					title: 'Kayıt olunurken bir hata oluştu.',
					message: 'Lütfen tekrar deneyin.',
					color: 'red',
				});
				return;
			}

			setEmail(input.email);
			setEmailSent(true);
		} catch (e) {
			notifications.show({
				title: 'Kayıt olunurken bir hata oluştu.',
				message: 'Lütfen tekrar deneyin.',
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

			const { data, errors } = await register.mutateAsync({ email });

			if (!data || !!errors.length) {
				notifications.show({
					title: 'E-posta gönderilirken bir hata oluştu.',
					message: 'Lütfen tekrar deneyin.',
					color: 'red',
				});
				return;
			}

			notifications.show({
				title: 'E-posta gönderildi.',
				message: 'Lütfen e-posta kutunuzu kontrol edin.',
				color: 'green',
			});
		} catch (e) {
			notifications.show({
				title: 'E-posta gönderilirken bir hata oluştu.',
				message: 'Lütfen tekrar deneyin.',
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
							alt="Kayıt Ol"
							src="/assets/register.png"
						/>

						<Stack gap="md" align="center">
							<Text ta="center" lh="1" fz="2rem" ff="var(--ff-title)">
								Ücretsiz hesap oluşturun
							</Text>
							<Text fz="sm" fw="200" ta="center">
								Birkaç adımda hesabınızı oluşturun ve hemen kullanmaya başlayın.
							</Text>
						</Stack>
					</Stack>

					<Divider />

					<RegisterForm onSubmit={handleSubmit} loading={register.isPending} />
				</Stack>
			</Paper>

			<Flex justify="center" pl="xs" gap=".25rem">
				<Text fz="xs" fw="200">
					Zaten bir hesabınız var mı?
				</Text>
				<Text component={Link} to={paths.login} fz="xs" fw="500" c="indigo">
					Giriş yapın
				</Text>
			</Flex>
		</Stack>
	);
}
