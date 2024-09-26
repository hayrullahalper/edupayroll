import type { FormikHelpers } from 'formik';
import type {
	LoginFormInput,
} from './LoginForm.utils';
import { Button, Checkbox, Flex, Stack, Text } from '@mantine/core';
import { IconAt, IconKey } from '@tabler/icons-react';
import { Form, FormikProvider, useFormik } from 'formik';
import { useTranslation } from 'react-i18next';

import { Link } from 'react-router-dom';
import PasswordField from '../../../fields/PasswordField';
import TextField from '../../../fields/TextField';

import paths from '../../paths';
import {
	loginFormInitialValues,
	loginFormSchema,
} from './LoginForm.utils';

interface LoginFormProps {
	onSubmit: (
		values: LoginFormInput,
		helpers: FormikHelpers<LoginFormInput>,
	) => Promise<void>;
}

export default function LoginForm({ onSubmit }: LoginFormProps) {
	const { t } = useTranslation();

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
					<TextField
						size="sm"
						name="email"
						inputMode="email"
						autoComplete="username"
						leftSection={<IconAt size={16} stroke={1.5} />}
						placeholder={t('auth.login.form.email.placeholder')}
					/>

					<PasswordField
						size="sm"
						maxLength={32}
						name="password"
						autoComplete="current-password"
						leftSection={<IconKey size={16} stroke={1.5} />}
						placeholder={t('auth.login.form.password.placeholder')}
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
									onChange={e =>
										formik.setFieldValue('remember', e.target.checked)}
								/>
								<Text fz="xs" fw="200">
									{t('auth.login.form.remember')}
								</Text>
							</Flex>

							<Text component={Link} to={paths.resetPassword} fw="200" fz="xs">
								{t('auth.login.form.forgotPassword')}
							</Text>
						</Flex>

						<Flex justify="flex-end">
							<Button
								type="submit"
								color="indigo"
								variant="light"
								loading={formik.isSubmitting}
							>
								{t('auth.login.form.submit')}
							</Button>
						</Flex>
					</Stack>
				</Stack>
			</Form>
		</FormikProvider>
	);
}
