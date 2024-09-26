import type { FormikHelpers } from 'formik';
import type { MouseEvent } from 'react';
import type { User } from '../../../../api';
import type {
	ProfileEmailUpdateFormInput,
} from './ProfileEmailUpdateForm.utils';
import { Button, Flex, Stack, Text } from '@mantine/core';
import { IconAt } from '@tabler/icons-react';

import { Form, FormikProvider, useFormik } from 'formik';
import { useTranslation } from 'react-i18next';
import TextField from '../../../../fields/TextField';

import useInputMode from '../../../../hooks/useInputMode';
import {
	profileEmailUpdateFormInitialValues,
	profileEmailUpdateFormSchema,
} from './ProfileEmailUpdateForm.utils';

interface ProfileEmailUpdateFormProps {
	user?: Pick<User, 'email'>;
	onSubmit: (
		values: ProfileEmailUpdateFormInput,
		helpers: FormikHelpers<ProfileEmailUpdateFormInput>,
	) => Promise<void>;
}

export default function ProfileEmailUpdateForm({
	user,
	onSubmit,
}: ProfileEmailUpdateFormProps) {
	const { t } = useTranslation();
	const [mode, setMode, inputProps] = useInputMode();

	const formik = useFormik({
		onSubmit,
		validateOnBlur: true,
		validateOnChange: false,
		validationSchema: profileEmailUpdateFormSchema,
		initialValues: {
			...profileEmailUpdateFormInitialValues,
			...user,
		},
	});

	const handleEdit = (e: MouseEvent) => {
		e.preventDefault();
		setMode('edit');
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
						name="email"
						maxLength={50}
						rightSection={<IconAt size={16} stroke={1.5} />}
						description={t('user.updateEmail.form.email.description')}
						label={
							<Text fw="200">{t('user.updateEmail.form.email.label')}</Text>
						}
						{...inputProps}
					/>

					<Flex mt={8} gap="md">
						{mode === 'view'
							? (
								<Button
									size="sm"
									type="button"
									variant="light"
									onClick={handleEdit}
								>
									{t('common.form.edit')}
								</Button>
							)
							: (
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
										disabled={formik.isSubmitting}
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
