import type { FormikHelpers } from 'formik';
import type {
	RegisterFormInput,
} from './RegisterForm.utils';
import { Button, Flex, Stack } from '@mantine/core';
import { IconAt } from '@tabler/icons-react';
import { Form, FormikProvider, useFormik } from 'formik';

import { useTranslation } from 'react-i18next';

import TextField from '../../../fields/TextField';
import {
	registerFormInitialValues,
	registerFormSchema,
} from './RegisterForm.utils';

interface RegisterFormProps {
	onSubmit: (
		values: RegisterFormInput,
		helpers: FormikHelpers<RegisterFormInput>,
	) => Promise<void>;
}

export default function RegisterForm({ onSubmit }: RegisterFormProps) {
	const { t } = useTranslation();

	const formik = useFormik({
		onSubmit,
		validateOnBlur: true,
		validateOnChange: false,
		validationSchema: registerFormSchema,
		initialValues: registerFormInitialValues,
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
						placeholder={t('auth.register.form.email.placeholder')}
					/>

					<Flex justify="flex-end">
						<Button
							type="submit"
							color="indigo"
							variant="light"
							loading={formik.isSubmitting}
						>
							{t('auth.register.form.submit')}
						</Button>
					</Flex>
				</Stack>
			</Form>
		</FormikProvider>
	);
}
