import type { FormikHelpers } from 'formik';
import type {
	ResetPasswordCompleteFormInput,
} from './ResetPasswordCompleteForm.utils';
import { Button, Stack } from '@mantine/core';
import { IconLock } from '@tabler/icons-react';
import { Form, FormikProvider, useFormik } from 'formik';

import { useTranslation } from 'react-i18next';

import PasswordField from '../../../fields/PasswordField';
import {
	resetPasswordCompleteFormInitialValues,
	resetPasswordCompleteFormSchema,
} from './ResetPasswordCompleteForm.utils';

interface ResetPasswordCompleteFormProps {
	onSubmit: (
		values: ResetPasswordCompleteFormInput,
		helpers: FormikHelpers<ResetPasswordCompleteFormInput>,
	) => Promise<void>;
}

export default function ResetPasswordCompleteForm({
	onSubmit,
}: ResetPasswordCompleteFormProps) {
	const { t } = useTranslation();

	const formik = useFormik({
		onSubmit,
		validateOnBlur: true,
		validateOnChange: false,
		validationSchema: resetPasswordCompleteFormSchema,
		initialValues: resetPasswordCompleteFormInitialValues,
	});

	return (
		<FormikProvider value={formik}>
			<Form noValidate onSubmit={formik.handleSubmit}>
				<Stack gap="sm">
					<PasswordField
						size="sm"
						maxLength={32}
						name="password"
						leftSection={<IconLock size={16} stroke={1.5} />}
						placeholder={t(
							'auth.resetPasswordComplete.form.password.placeholder',
						)}
					/>

					<Button
						type="submit"
						color="indigo"
						variant="light"
						loading={formik.isSubmitting}
					>
						{t('auth.resetPasswordComplete.form.submit')}
					</Button>
				</Stack>
			</Form>
		</FormikProvider>
	);
}
