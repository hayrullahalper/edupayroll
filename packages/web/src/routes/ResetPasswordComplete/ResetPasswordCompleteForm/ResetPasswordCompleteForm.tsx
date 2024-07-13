import { Button, Stack } from '@mantine/core';
import { IconLock } from '@tabler/icons-react';
import { useTranslation } from 'react-i18next';
import { Form, FormikHelpers, FormikProvider, useFormik } from 'formik';

import PasswordField from '../../../fields/PasswordField';

import {
	ResetPasswordCompleteFormInput,
	resetPasswordCompleteFormSchema,
	resetPasswordCompleteFormInitialValues,
} from './ResetPasswordCompleteForm.utils';

interface ResetPasswordCompleteFormProps {
	loading?: boolean;
	onSubmit: (
		values: ResetPasswordCompleteFormInput,
		helpers: FormikHelpers<ResetPasswordCompleteFormInput>,
	) => void;
}

export default function ResetPasswordCompleteForm({
	loading,
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
						loading={loading}
					>
						{t('auth.resetPasswordComplete.form.submit')}
					</Button>
				</Stack>
			</Form>
		</FormikProvider>
	);
}
