import { IconAt } from '@tabler/icons-react';
import { Button, Flex, Stack, TextInput } from '@mantine/core';
import { Form, FormikHelpers, FormikProvider, useFormik } from 'formik';

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
					<TextInput
						size="sm"
						inputMode="email"
						autoComplete="username"
						error={formik.errors.email}
						placeholder="E-posta adresiniz"
						leftSection={<IconAt size={16} stroke={1.5} />}
						{...formik.getFieldProps('email')}
					/>

					<Flex justify="flex-end">
						<Button
							type="submit"
							color="indigo"
							variant="light"
							loading={loading}
						>
							Kayıt Ol
						</Button>
					</Flex>
				</Stack>
			</Form>
		</FormikProvider>
	);
}
