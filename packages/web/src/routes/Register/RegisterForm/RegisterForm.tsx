import { IconAt } from '@tabler/icons-react';
import { useTranslation } from 'react-i18next';
import { Button, Flex, Stack } from '@mantine/core';
import { Form, FormikHelpers, FormikProvider, useFormik } from 'formik';

import TextField from '../../../fields/TextField';

import {
	RegisterFormInput,
	registerFormSchema,
	registerFormInitialValues,
} from './RegisterForm.utils';

interface RegisterFormProps {
	loading?: boolean;
	onSubmit: (
		values: RegisterFormInput,
		helpers: FormikHelpers<RegisterFormInput>,
	) => void;
}

export default function RegisterForm({ loading, onSubmit }: RegisterFormProps) {
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
							loading={loading}
						>
							{t('auth.register.form.submit')}
						</Button>
					</Flex>
				</Stack>
			</Form>
		</FormikProvider>
	);
}
