import { Divider, Stack } from '@mantine/core';
import { useQuery } from '@tanstack/react-query';

import { client } from '../../api';

import ProfileEmail from './ProfileEmail';
import ProfileInformations from './ProfileInformations';

export default function Profile() {
	useQuery({
		queryKey: ['school'],
		queryFn: () => client('school').getSchool(),
	});

	return (
		<Stack gap="md">
			<ProfileInformations />
			<Divider />
			<ProfileEmail />
		</Stack>
	);
}
