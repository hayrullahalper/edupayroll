import {
	Text,
	Flex,
	Stack,
	Button,
	Checkbox,
	TextInput,
	PasswordInput,
} from '@mantine/core';
import { Link } from 'react-router-dom';
import { IconAt, IconKey } from '@tabler/icons-react';
import { Form, FormikHelpers, FormikProvider, useFormik } from 'formik';

import paths from '../../paths';

import {
	LoginFormInput,
	loginFormSchema,
	loginFormInitialValues,
} from './LoginForm.utils';

interface LoginFormProps {
	loading?: boolean;
	onSubmit: (
		values: LoginFormInput,
		helpers: FormikHelpers<LoginFormInput>,
	) => void;
}

export default function LoginForm({ loading, onSubmit }: LoginFormProps) {
	const formik = useFormik({
		onSubmit,
		validateOnBlur: true,
		validateOnChange: false,
		validationSchema: loginFormSchema,
		initialValues: loginFormInitialValues,
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

					<PasswordInput
						size="sm"
						maxLength={32}
						placeholder="Şifreniz"
						error={formik.errors.password}
						autoComplete="current-password"
						leftSection={<IconKey size={16} stroke={1.5} />}
						{...formik.getFieldProps('password')}
					/>

					<Stack gap="sm">
						<Flex justify="space-between" align="center">
							<Flex
								gap=".32rem"
								align="center"
								justify="center"
								component="label"
							>
								<Checkbox
									size="xs"
									color="indigo"
									onChange={(e) =>
										formik.setFieldValue('remember', e.target.checked)
									}
								/>
								<Text fz="xs" fw="200">
									Beni Hatırla
								</Text>
							</Flex>

							<Text component={Link} to={paths.resetPassword} fw="200" fz="xs">
								Şifrenizi mi unuttunuz?
							</Text>
						</Flex>

						<Flex justify="flex-end">
							<Button
								type="submit"
								color="indigo"
								variant="light"
								loading={loading}
							>
								Giriş Yap
							</Button>
						</Flex>
					</Stack>
				</Stack>
			</Form>
		</FormikProvider>
	);
}
