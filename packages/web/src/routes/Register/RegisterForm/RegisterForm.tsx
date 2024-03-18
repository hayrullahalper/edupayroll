import { IconAt } from '@tabler/icons-react';
import { Button, Flex, TextInput } from '@mantine/core';

export default function RegisterForm() {
	return (
		<>
			<TextInput
				size="sm"
				inputMode="email"
				autoComplete="off"
				placeholder="Email adresiniz"
				leftSection={<IconAt size={16} stroke={1.5} />}
			/>

			<Flex justify="flex-end">
				<Button variant="light" color="indigo">
					Kayıt Ol
				</Button>
			</Flex>
		</>
	);
}
