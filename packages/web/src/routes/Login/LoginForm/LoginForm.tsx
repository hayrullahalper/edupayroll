import {
	Button,
	Checkbox,
	Flex,
	PasswordInput,
	Stack,
	Text,
	TextInput,
} from '@mantine/core';
import { Link } from 'react-router-dom';
import { IconAt, IconKey } from '@tabler/icons-react';

import paths from '../../paths';

export default function LoginForm() {
	return (
		<>
			<TextInput
				size="sm"
				inputMode="email"
				autoComplete="username"
				placeholder="Email adresiniz"
				leftSection={<IconAt size={16} stroke={1.5} />}
			/>
			<PasswordInput
				size="sm"
				placeholder="Şifreniz"
				autoComplete="current-password"
				leftSection={<IconKey size={16} stroke={1.5} />}
			/>

			<Stack gap="sm">
				<Flex justify="space-between" align="center">
					<Flex component="label" gap=".32rem" justify="center" align="center">
						<Checkbox size="xs" color="indigo" />
						<Text fz="xs" fw="200">
							Beni Hatırla
						</Text>
					</Flex>

					<Text component={Link} to={paths.forgotPassword} fw="200" fz="xs">
						Şifrenizi mi unuttunuz?
					</Text>
				</Flex>

				<Flex justify="flex-end">
					<Button variant="light" color="indigo">
						Giriş Yap
					</Button>
				</Flex>
			</Stack>
		</>
	);
}
