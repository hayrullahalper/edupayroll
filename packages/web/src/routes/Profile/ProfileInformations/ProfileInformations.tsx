import type { UserUpdateNameInput } from '../../../api';
import type {
	ProfileNameUpdateFormInput,
} from './ProfileNameUpdateForm';
import { Alert, Stack, Text } from '@mantine/core';
import { notifications } from '@mantine/notifications';
import { IconUserCheck } from '@tabler/icons-react';
import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { client } from '../../../api';
import { useUser } from '../../../contexts/user';

import generateKey from '../../../helpers/generateKey';
import ProfileNameUpdateForm from './ProfileNameUpdateForm';

export default function ProfileInformations() {
	const user = useUser();
	const { t } = useTranslation();
	const queryClient = useQueryClient();

	const [success, setSuccess] = useState(false);

	const updateName = useMutation({
		mutationFn: (userUpdateNameInput: UserUpdateNameInput) =>
			client.user.updateName({ userUpdateNameInput }),
		onSuccess: response =>
			!response.errors.length && queryClient.setQueryData(['user'], response),
	});

	const handleSubmit = async (input: ProfileNameUpdateFormInput) => {
		try {
			const { data, errors } = await updateName.mutateAsync(input);

			if (!data || !!errors.length) {
				notifications.show({
					message: t('common.error.unknown'),
					color: 'red',
				});
				return;
			}

			setSuccess(true);
		}
		catch (e) {
			notifications.show({
				message: t('common.error.unknown'),
				color: 'red',
			});
		}
	};

	return (
		<Stack gap="sm">
			<ProfileNameUpdateForm
				user={user}
				key={generateKey(user)}
				onSubmit={handleSubmit}
			/>

			{success && (
				<Alert
					color="teal"
					variant="light"
					withCloseButton
					icon={<IconUserCheck />}
					onClose={() => setSuccess(false)}
				>
					<Text fz="sm" c="teal" fw="normal">
						{t('user.updateName.form.success')}
					</Text>
				</Alert>
			)}
		</Stack>
	);
}
