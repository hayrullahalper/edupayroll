import { IconAt } from '@tabler/icons-react';
import { Button, Stack } from '@mantine/core';
import { useTranslation } from 'react-i18next';
import { Form, FormikHelpers, FormikProvider, useFormik } from 'formik';

import TextField from '../../../fields/TextField';

import {
	ResetPasswordFormInput,
	resetPasswordFormSchema,
	resetPasswordFormInitialValues,
} from './ResetPasswordForm.utils';

interface ResetPasswordFormProps {
	loading?: boolean;
	onSubmit: (
		values: ResetPasswordFormInput,
		helpers: FormikHelpers<ResetPasswordFormInput>,
	) => void;
}

export default function ResetPasswordForm({
	loading,
	onSubmit,
}: ResetPasswordFormProps) {
	const { t } = useTranslation();

	const formik = useFormik({
		onSubmit,
		validateOnBlur: true,
		validateOnChange: false,
		validationSchema: resetPasswordFormSchema,
		initialValues: resetPasswordFormInitialValues,
	});

	return (
		<FormikProvider value={formik}>
			<Form noValidate onSubmit={formik.handleSubmit}>
				<Stack gap="sm">
					<TextField
						size="sm"
						name="email"
						inputMode="email"
						autoComplete="username"
						leftSection={<IconAt size={16} stroke={1.5} />}
						placeholder={t('auth.resetPassword.form.email.placeholder')}
					/>

					<Button
						type="submit"
						color="indigo"
						variant="light"
						loading={loading}
					>
						{t('auth.resetPassword.form.submit')}
					</Button>
				</Stack>
			</Form>
		</FormikProvider>
	);
}
