import { Link } from 'react-router-dom';
import { Flex, Text, Paper, Stack, Divider, Image } from '@mantine/core';

import paths from '../paths';

import LoginForm from './LoginForm';

export default function Login() {
	return (
		<Stack gap="0.25rem">
			<Paper w={324} bg="gray.1" p="lg">
				<Stack gap="sm">
					<Stack mt="xs" gap="0" align="center">
						<Image ml="-.75rem" w="6rem" src="/auth.png" />

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

					<LoginForm />
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
