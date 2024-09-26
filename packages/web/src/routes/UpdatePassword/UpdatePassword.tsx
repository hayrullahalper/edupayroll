import type { FormikHelpers } from 'formik';
import type { UserUpdatePasswordInput } from '../../api';
import type {
	UpdatePasswordFormInput,
} from './UpdatePassword.utils';
import { Alert, Button, Divider, Flex, Stack, Text } from '@mantine/core';
import { notifications } from '@mantine/notifications';
import { IconLockCheck } from '@tabler/icons-react';
import { useMutation } from '@tanstack/react-query';
import { Form, FormikProvider, useFormik } from 'formik';

import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { client } from '../../api';

import PasswordField from '../../fields/PasswordField';
import {
	updatePasswordFormInitialValues,
	updatePasswordFormSchema,
} from './UpdatePassword.utils';

export default function UpdatePassword() {
	const { t } = useTranslation();
	const [success, setSuccess] = useState(false);

	const updatePassword = useMutation({
		mutationFn: (userUpdatePasswordInput: UserUpdatePasswordInput) =>
			client.user.updatePassword({ userUpdatePasswordInput }),
	});

	const handleSubmit = async (
		input: UpdatePasswordFormInput,
		helpers: FormikHelpers<UpdatePasswordFormInput>,
	) => {
		try {
			const { data, errors } = await updatePassword.mutateAsync(input);

			if (!data?.success || !!errors.length) {
				if (errors.some(({ code }) => code === 'PASSWORD_MISMATCH')) {
					helpers.setErrors({
						currentPassword: t('user.updatePassword.form.passwordMismatch'),
					});
					return;
				}

				notifications.show({
					message: t('common.error.unknown'),
					color: 'red',
				});
				return;
			}

			helpers.resetForm();
			setSuccess(true);
		}
		catch (e) {
			notifications.show({
				message: t('common.error.unknown'),
				color: 'red',
			});
		}
	};

	const formik = useFormik({
		onSubmit: handleSubmit,
		validateOnBlur: true,
		validateOnChange: false,
		validationSchema: updatePasswordFormSchema,
		initialValues: updatePasswordFormInitialValues,
	});

	return (
		<FormikProvider value={formik}>
			<Form noValidate onSubmit={formik.handleSubmit}>
				<Stack gap="sm">
					<PasswordField
						maxLength={32}
						name="currentPassword"
						autoComplete="current-password"
						description={t(
							'user.updatePassword.form.currentPassword.description',
						)}
						label={(
							<Text fw="200">
								{t('user.updatePassword.form.currentPassword.label')}
							</Text>
						)}
					/>

					<PasswordField
						maxLength={32}
						name="newPassword"
						autoComplete="new-password"
						description={t('user.updatePassword.form.newPassword.description')}
						label={(
							<Text fw="200">
								{t('user.updatePassword.form.newPassword.label')}
							</Text>
						)}
					/>

					<PasswordField
						maxLength={32}
						name="confirmPassword"
						autoComplete="new-password"
						description={t(
							'user.updatePassword.form.confirmPassword.description',
						)}
						label={(
							<Text fw="200">
								{t('user.updatePassword.form.confirmPassword.label')}
							</Text>
						)}
					/>

					<Flex mt={8}>
						<Button
							size="sm"
							type="submit"
							variant="light"
							loading={formik.isSubmitting}
						>
							{t('user.updatePassword.form.submit')}
						</Button>
					</Flex>

					{success && (
						<Stack gap="sm">
							<Divider />
							<Alert
								color="teal"
								variant="light"
								withCloseButton
								icon={<IconLockCheck />}
								onClose={() => setSuccess(false)}
							>
								<Text fz="sm" c="teal" fw="normal">
									{t('user.updatePassword.form.success')}
								</Text>
							</Alert>
						</Stack>
					)}
				</Stack>
			</Form>
		</FormikProvider>
	);
}
