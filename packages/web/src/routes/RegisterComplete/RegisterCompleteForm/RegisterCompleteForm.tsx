import type { FormikHelpers } from 'formik';
import type {
	RegisterCompleteFormInput,
} from './RegisterCompleteForm.utils';
import { Button, Divider, Flex, Stack } from '@mantine/core';
import {
	IconBuildingCommunity,
	IconIdBadge2,
	IconLock,
	IconUserBolt,
	IconUserScan,
} from '@tabler/icons-react';
import { Form, FormikProvider, useFormik } from 'formik';

import { useTranslation } from 'react-i18next';
import PasswordField from '../../../fields/PasswordField';

import TextField from '../../../fields/TextField';
import {
	registerCompleteFormInitialValues,
	registerCompleteFormSchema,
} from './RegisterCompleteForm.utils';

interface RegisterCompleteFormProps {
	onSubmit: (
		values: RegisterCompleteFormInput,
		helpers: FormikHelpers<RegisterCompleteFormInput>,
	) => Promise<void>;
}

export default function RegisterCompleteForm({
	onSubmit,
}: RegisterCompleteFormProps) {
	const { t } = useTranslation();

	const formik = useFormik({
		onSubmit,
		validateOnBlur: true,
		validateOnChange: false,
		validationSchema: registerCompleteFormSchema,
		initialValues: registerCompleteFormInitialValues,
	});

	return (
		<FormikProvider value={formik}>
			<Form noValidate onSubmit={formik.handleSubmit}>
				<Stack gap="sm">
					<TextField
						size="sm"
						maxLength={50}
						inputMode="text"
						name="firstName"
						leftSection={<IconUserScan size={16} stroke={1.5} />}
						placeholder={t('auth.registerComplete.form.firstName.placeholder')}
					/>
					<TextField
						size="sm"
						maxLength={50}
						name="lastName"
						inputMode="text"
						leftSection={<IconUserScan size={16} stroke={1.5} />}
						placeholder={t('auth.registerComplete.form.lastName.placeholder')}
					/>
					<TextField
						size="sm"
						name="title"
						maxLength={50}
						inputMode="text"
						leftSection={<IconIdBadge2 size={16} stroke={1.5} />}
						placeholder={t('auth.registerComplete.form.title.placeholder')}
					/>

					<Divider />

					<TextField
						size="sm"
						maxLength={50}
						inputMode="text"
						name="schoolName"
						leftSection={<IconBuildingCommunity size={16} stroke={1.5} />}
						placeholder={t('auth.registerComplete.form.schoolName.placeholder')}
					/>
					<TextField
						size="sm"
						maxLength={50}
						inputMode="text"
						name="principalName"
						leftSection={<IconUserBolt size={16} stroke={1.5} />}
						placeholder={t(
							'auth.registerComplete.form.principalName.placeholder',
						)}
					/>

					<Divider />

					<PasswordField
						size="sm"
						maxLength={32}
						name="password"
						leftSection={<IconLock size={16} stroke={1.5} />}
						placeholder={t('auth.registerComplete.form.password.placeholder')}
					/>

					<Flex justify="flex-end">
						<Button
							type="submit"
							color="indigo"
							variant="light"
							loading={formik.isSubmitting}
						>
							{t('auth.registerComplete.form.submit')}
						</Button>
					</Flex>
				</Stack>
			</Form>
		</FormikProvider>
	);
}
