import { FormikHelpers } from 'formik';
import { useRef, useState } from 'react';
import { useDisclosure } from '@mantine/hooks';
import { useTranslation } from 'react-i18next';
import { Alert, Stack, Text } from '@mantine/core';
import { IconMailCheck } from '@tabler/icons-react';
import { notifications } from '@mantine/notifications';
import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useUser } from '../../../contexts/user';
import generateKey from '../../../helpers/generateKey';
import { client, UserUpdateEmailInput } from '../../../api';

import ProfileEmailUpdateForm, {
	ProfileEmailUpdateFormInput,
} from './ProfileEmailUpdateForm';
import ProfileEmailUpdateModal, {
	ProfileEmailUpdateModalFormInput,
} from './ProfileEmailUpdateModal';

export default function ProfileEmail() {
	const user = useUser();
	const { t } = useTranslation();
	const queryClient = useQueryClient();

	const formInput = useRef<ProfileEmailUpdateFormInput>();
	const formHelpers = useRef<FormikHelpers<ProfileEmailUpdateFormInput>>();

	const [opened, disclosure] = useDisclosure();
	const [success, setSuccess] = useState(false);

	const updateEmail = useMutation({
		mutationFn: (userUpdateEmailInput: UserUpdateEmailInput) =>
			client('user').updateEmail({ userUpdateEmailInput }),
		onSuccess: (data) =>
			!data.errors.length && queryClient.setQueryData(['user'], data),
	});

	const handleFormSubmit = async (
		input: ProfileEmailUpdateFormInput,
		helpers: FormikHelpers<ProfileEmailUpdateFormInput>,
	) => {
		formInput.current = input;
		formHelpers.current = helpers;

		disclosure.open();
	};

	const handleModalSubmit = async (
		input: ProfileEmailUpdateModalFormInput,
		helpers: FormikHelpers<ProfileEmailUpdateModalFormInput>,
	) => {
		if (!formInput.current || !formHelpers.current) {
			return;
		}

		try {
			const { node, errors } = await updateEmail.mutateAsync({
				...formInput.current,
				...input,
			});

			if (!node || !!errors.length) {
				if (errors[0].code === 'PASSWORD_MISMATCH') {
					helpers.setErrors({
						password: t('user.updateEmail.modal.password.mismatch'),
					});
					return;
				}

				if (errors[0].code === 'USER_EMAIL_EXISTS') {
					formHelpers.current.setErrors({
						email: t('user.updateEmail.form.email.alreadyExists'),
					});
					disclosure.close();
					return;
				}

				notifications.show({
					message: t('common.error.unknown'),
					color: 'red',
				});
				return;
			}

			setSuccess(true);

			disclosure.close();
			formHelpers.current?.resetForm();
		} catch (e) {
			notifications.show({
				message: t('common.error.unknown'),
				color: 'red',
			});
		}
	};

	return (
		<Stack gap="sm">
			<ProfileEmailUpdateForm
				user={user}
				key={generateKey(user)}
				onSubmit={handleFormSubmit}
			/>

			{success && (
				<Alert
					color="teal"
					variant="light"
					withCloseButton
					icon={<IconMailCheck />}
					onClose={() => setSuccess(false)}
				>
					<Text fz="sm" c="teal" fw="normal">
						{t('user.updateEmail.form.success')}
					</Text>
				</Alert>
			)}

			{opened && (
				<ProfileEmailUpdateModal
					onClose={disclosure.close}
					onSubmit={handleModalSubmit}
				/>
			)}
		</Stack>
	);
}
