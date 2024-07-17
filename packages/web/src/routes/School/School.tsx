import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Alert, Stack, Text } from '@mantine/core';
import { notifications } from '@mantine/notifications';
import { IconSettingsCheck } from '@tabler/icons-react';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

import generateKey from '../../helpers/generateKey';
import { client, SchoolUpdateInput } from '../../api';

import SchoolUpdateForm, { SchoolUpdateFormInput } from './SchoolUpdateForm';

export default function School() {
	const { t } = useTranslation();
	const queryClient = useQueryClient();
	const [success, setSuccess] = useState(false);

	const { data: { node: school } = {} } = useQuery({
		queryKey: ['school'],
		queryFn: () => client('school').getSchool(),
	});

	const updateSchool = useMutation({
		mutationFn: (schoolUpdateInput: SchoolUpdateInput) =>
			client('school').updateSchool({ schoolUpdateInput }),
		onSuccess: (data) =>
			!data.errors.length && queryClient.setQueryData(['school'], data),
	});

	const handleSubmit = async (input: SchoolUpdateFormInput) => {
		try {
			if (!school) {
				return;
			}

			const { node, errors } = await updateSchool.mutateAsync(input);

			if (!node || !!errors.length) {
				notifications.show({
					message: t('common.error.unknown'),
					color: 'red',
				});
				return;
			}

			setSuccess(true);
		} catch (e) {
			notifications.show({
				message: t('common.error.unknown'),
				color: 'red',
			});
		}
	};

	return (
		<Stack gap="sm">
			<SchoolUpdateForm
				school={school}
				onSubmit={handleSubmit}
				key={generateKey(school)}
			/>

			{success && (
				<Alert
					color="teal"
					variant="light"
					withCloseButton
					icon={<IconSettingsCheck />}
					onClose={() => setSuccess(false)}
				>
					<Text fz="sm" c="teal" fw="normal">
						{t('user.updateSchool.form.success')}
					</Text>
				</Alert>
			)}
		</Stack>
	);
}
