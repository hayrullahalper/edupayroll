import {
	IconLock,
	IconIdBadge2,
	IconUserBolt,
	IconUserScan,
	IconBuildingCommunity,
} from '@tabler/icons-react';
import { useTranslation } from 'react-i18next';
import { Flex, Stack, Button, Divider } from '@mantine/core';
import { Form, FormikHelpers, FormikProvider, useFormik } from 'formik';

import TextField from '../../../fields/TextField';
import PasswordField from '../../../fields/PasswordField';

import {
	RegisterCompleteFormInput,
	registerCompleteFormSchema,
	registerCompleteFormInitialValues,
} from './RegisterCompleteForm.utils';

interface RegisterCompleteFormProps {
	loading?: boolean;
	onSubmit: (
		values: RegisterCompleteFormInput,
		helpers: FormikHelpers<RegisterCompleteFormInput>,
	) => void;
}

export default function RegisterCompleteForm({
	loading,
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
							loading={loading}
						>
							{t('auth.registerComplete.form.submit')}
						</Button>
					</Flex>
				</Stack>
			</Form>
		</FormikProvider>
	);
}
