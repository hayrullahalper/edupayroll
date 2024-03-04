import { Button, Paper, Stack } from '@mantine/core';

import Header from '../../components/Header';

export default function Documents() {
	return (
		<Stack gap="xs">
			<Header title="Belgeler">
				<Button size="xs" color="indigo">
					Yeni Belge Oluştur
				</Button>
			</Header>
			<Paper py="sm" px="md">
				asd
			</Paper>
		</Stack>
	);
}
