import { useState } from 'react';
import { useMutation } from '@tanstack/react-query';
import { IconLockCancel } from '@tabler/icons-react';
import { Link, useNavigate } from 'react-router-dom';
import { notifications } from '@mantine/notifications';
import { Flex, Text, Paper, Stack, Divider, Alert } from '@mantine/core';

import paths from '../paths';
import { client, LoginInput } from '../../api';
import useToken from '../../contexts/token/useToken';

import LoginForm, { LoginFormInput } from './LoginForm';

export default function Login() {
	const navigate = useNavigate();
	const { setToken } = useToken();
	const [error, setError] = useState<string | null>(null);

	const login = useMutation({
		mutationFn: (loginInput: LoginInput) =>
			client('auth').login({ loginInput }),
	});

	const handleSubmit = async (input: LoginFormInput) => {
		try {
			const { data, errors } = await login.mutateAsync(input);

			if (!data || !!errors.length) {
				if (errors.some(({ code }) => code === 'INVALID_CREDENTIALS')) {
					setError('Kullanıcı adı veya şifre hatalı.');
					return;
				}

				notifications.show({
					title: 'Giriş yapılırken bir hata oluştu.',
					message: 'Lütfen tekrar deneyin.',
					color: 'red',
				});
				return;
			}

			setToken(data.token, input.remember);
			navigate(paths.documents);
		} catch (e) {
			notifications.show({
				title: 'Giriş yapılırken bir hata oluştu.',
				message: 'Lütfen tekrar deneyin.',
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
							alt="Giriş Yap"
							src="/assets/auth.png"
						/>

						<Stack px="3rem" gap="0" align="center">
							<Text fz="2rem" ff="var(--ff-title)">
								Hoşgeldiniz!
							</Text>
							<Text fz="sm" fw="200" ta="center">
								Lütfen giriş yapmak için bilgilerinizi girin.
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

					<LoginForm loading={login.isPending} onSubmit={handleSubmit} />
				</Stack>
			</Paper>
			<Flex justify="center" pl="xs" gap=".25rem">
				<Text fz="xs" fw="200">
					Hesabınız yok mu?
				</Text>
				<Text component={Link} to={paths.register} fz="xs" fw="500" c="indigo">
					Kayıt olun
				</Text>
			</Flex>
		</Stack>
	);
}
