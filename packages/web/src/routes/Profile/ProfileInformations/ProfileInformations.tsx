import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Alert, Stack, Text } from '@mantine/core';
import { IconUserCheck } from '@tabler/icons-react';
import { notifications } from '@mantine/notifications';
import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useUser } from '../../../contexts/user';
import generateKey from '../../../helpers/generateKey';
import { client, UserUpdateNameInput } from '../../../api';

import ProfileNameUpdateForm, {
	ProfileNameUpdateFormInput,
} from './ProfileNameUpdateForm';

export default function ProfileInformations() {
	const user = useUser();
	const { t } = useTranslation();
	const queryClient = useQueryClient();

	const [success, setSuccess] = useState(false);

	const updateName = useMutation({
		mutationFn: (userUpdateNameInput: UserUpdateNameInput) =>
			client('user').updateName({ userUpdateNameInput }),
		onSuccess: (data) =>
			!data.errors.length && queryClient.setQueryData(['user'], data),
	});

	const handleSubmit = async (input: ProfileNameUpdateFormInput) => {
		try {
			const { node, errors } = await updateName.mutateAsync(input);

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
