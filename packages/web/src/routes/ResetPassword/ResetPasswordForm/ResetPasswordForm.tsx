import type { FormikHelpers } from 'formik';
import type {
	ResetPasswordFormInput,
} from './ResetPasswordForm.utils';
import { Button, Stack } from '@mantine/core';
import { IconAt } from '@tabler/icons-react';
import { Form, FormikProvider, useFormik } from 'formik';

import { useTranslation } from 'react-i18next';

import TextField from '../../../fields/TextField';
import {
	resetPasswordFormInitialValues,
	resetPasswordFormSchema,
} from './ResetPasswordForm.utils';

interface ResetPasswordFormProps {
	onSubmit: (
		values: ResetPasswordFormInput,
		helpers: FormikHelpers<ResetPasswordFormInput>,
	) => Promise<void>;
}

export default function ResetPasswordForm({
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
						loading={formik.isSubmitting}
					>
						{t('auth.resetPassword.form.submit')}
					</Button>
				</Stack>
			</Form>
		</FormikProvider>
	);
}
