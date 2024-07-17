import { MouseEvent, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { IconUserScan } from '@tabler/icons-react';
import { Button, Flex, Stack, Text } from '@mantine/core';
import { Form, FormikHelpers, FormikProvider, useFormik } from 'formik';

import { User } from '../../../../api';
import TextField from '../../../../fields/TextField';
import useInputMode from '../../../../hooks/useInputMode';

import {
	ProfileNameUpdateFormInput,
	profileNameUpdateFormSchema,
	profileNameUpdateFormInitialValues,
} from './ProfileNameUpdateForm.utils';

interface ProfileNameUpdateFormProps {
	user?: Pick<User, 'firstName' | 'lastName'>;
	onSubmit: (
		values: ProfileNameUpdateFormInput,
		helpers: FormikHelpers<ProfileNameUpdateFormInput>,
	) => Promise<void>;
}

export default function ProfileNameUpdateForm({
	user,
	onSubmit,
}: ProfileNameUpdateFormProps) {
	const { t } = useTranslation();
	const [mode, setMode, inputProps] = useInputMode();

	const inputRef = useRef<HTMLInputElement>(null);

	const formik = useFormik<ProfileNameUpdateFormInput>({
		validateOnBlur: true,
		validateOnChange: false,
		validationSchema: profileNameUpdateFormSchema,
		onSubmit: async (values, helpers) => {
			await onSubmit(values, helpers);
			setMode('view');
		},
		initialValues: {
			...profileNameUpdateFormInitialValues,
			...user,
		},
	});

	const handleEdit = (e: MouseEvent) => {
		e.preventDefault();
		setMode('edit');
		inputRef.current?.focus();
	};

	const handleCancel = () => {
		formik.resetForm();
		setMode('view');
	};

	return (
		<FormikProvider value={formik}>
			<Form noValidate onSubmit={formik.handleSubmit}>
				<Stack gap="sm">
					<TextField
						maxLength={50}
						ref={inputRef}
						name="firstName"
						rightSection={<IconUserScan size={18} stroke={1.5} />}
						description={t('user.updateName.form.firstName.description')}
						label={
							<Text fw="200">{t('user.updateName.form.firstName.label')}</Text>
						}
						{...inputProps}
					/>

					<TextField
						maxLength={50}
						name="lastName"
						rightSection={<IconUserScan size={18} stroke={1.5} />}
						description={t('user.updateName.form.lastName.description')}
						label={
							<Text fw="200">{t('user.updateName.form.lastName.label')}</Text>
						}
						{...inputProps}
					/>

					<Flex mt={8} gap="md">
						{mode === 'view' ? (
							<Button
								size="sm"
								type="button"
								variant="light"
								onClick={handleEdit}
							>
								{t('common.form.edit')}
							</Button>
						) : (
							<>
								<Button
									size="sm"
									color="teal"
									type="submit"
									variant="light"
									loading={formik.isSubmitting}
									disabled={!formik.isValid || !formik.dirty}
								>
									{t('common.form.submit')}
								</Button>
								<Button
									size="sm"
									color="red"
									type="reset"
									variant="subtle"
									onClick={handleCancel}
								>
									{t('common.form.cancel')}
								</Button>
							</>
						)}
					</Flex>
				</Stack>
			</Form>
		</FormikProvider>
	);
}
