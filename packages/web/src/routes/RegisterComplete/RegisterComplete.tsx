import { useMutation } from '@tanstack/react-query';
import { notifications } from '@mantine/notifications';
import { Navigate, useNavigate, useSearchParams } from 'react-router-dom';
import { Box, Divider, Paper, Stack, Text } from '@mantine/core';

import paths from '../paths';
import { useToken } from '../../contexts/token';
import { client, RegisterCompleteInput } from '../../api';

import RegisterCompleteForm, {
	RegisterCompleteFormInput,
} from './RegisterCompleteForm';

export default function RegisterComplete() {
	const navigate = useNavigate();
	const { setToken } = useToken();
	const [searchParams] = useSearchParams();

	const token = searchParams.get('token');

	const registerComplete = useMutation({
		mutationFn: (registerCompleteInput: RegisterCompleteInput) =>
			client('auth').registerComplete({ registerCompleteInput }),
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

			if (!data || !!errors.length) {
				notifications.show({
					title: 'Kayıt olunurken bir hata oluştu.',
					message: 'Lütfen tekrar deneyin.',
					color: 'red',
				});
				return;
			}

			setToken(data.token, true);
			navigate(paths.documents);
		} catch (e) {
			notifications.show({
				title: 'Kayıt olunurken bir hata oluştu.',
				message: 'Lütfen tekrar deneyin.',
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
							alt="Kayıt Ol"
							component="img"
							src="/assets/register-complete.png"
						/>

						<Stack gap="md" align="center">
							<Text ta="center" lh="1" fz="2rem" ff="var(--ff-title)">
								Hesap Bilgilerinizi Tamamlayın
							</Text>
							<Text fz="sm" fw="200" ta="center">
								Son adım! Hesabınızı tamamlamak için lütfen bilgilerinizi girin.
							</Text>
						</Stack>
					</Stack>

					<Divider />

					<RegisterCompleteForm
						onSubmit={handleSubmit}
						loading={registerComplete.isPending}
					/>
				</Stack>
			</Paper>
		</Stack>
	);
}
