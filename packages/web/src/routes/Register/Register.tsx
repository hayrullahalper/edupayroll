import { Link } from 'react-router-dom';
import { Divider, Flex, Image, Paper, Stack, Text } from '@mantine/core';

import paths from '../paths';

import RegisterForm from './RegisterForm';

export default function Register() {
	return (
		<Stack gap="0.25rem">
			<Paper w={324} bg="gray.1" p="lg">
				<Stack gap="md">
					<Stack gap="0" align="center">
						<Image w="10rem" src="/register.png" />

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

					<RegisterForm />
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
