import { MouseEvent } from 'react';
import { useTranslation } from 'react-i18next';
import { Form, FormikHelpers, FormikProvider, useFormik } from 'formik';
import { Button, Divider, Flex, Modal, Stack, Text } from '@mantine/core';

import PasswordField from '../../../../fields/PasswordField';

import {
	ProfileEmailUpdateModalFormInput,
	profileEmailUpdateModalFormSchema,
	profileEmailUpdateModalFormInitialValues,
} from './ProfileEmailUpdateModal.utils';

interface ProfileEmailUpdateModalProps {
	onClose: () => void;
	onSubmit: (
		values: ProfileEmailUpdateModalFormInput,
		helpers: FormikHelpers<ProfileEmailUpdateModalFormInput>,
	) => Promise<void>;
}

export default function ProfileEmailUpdateModal({
	onClose,
	onSubmit,
}: ProfileEmailUpdateModalProps) {
	const { t } = useTranslation();

	const formik = useFormik({
		onSubmit,
		validateOnBlur: true,
		validateOnChange: false,
		validationSchema: profileEmailUpdateModalFormSchema,
		initialValues: profileEmailUpdateModalFormInitialValues,
	});

	const handleCancel = (e: MouseEvent) => {
		e.preventDefault();
		onClose();
	};

	return (
		<Modal
			opened
			centered
			size="sm"
			onClose={onClose}
			closeButtonProps={{ tabIndex: -1 }}
			title={t('user.updateEmail.modal.title')}
		>
			<FormikProvider value={formik}>
				<Form noValidate onSubmit={formik.handleSubmit}>
					<Stack gap="sm" pt="lg">
						<PasswordField
							autoFocus
							maxLength={32}
							name="password"
							autoComplete="current-password"
							description={t('user.updateEmail.modal.password.description')}
							label={
								<Text fw="200">
									{t('user.updateEmail.modal.password.label')}
								</Text>
							}
						/>

						<Divider />

						<Flex gap="sm" justify="flex-end">
							<Button
								color="red"
								type="button"
								variant="subtle"
								onClick={handleCancel}
							>
								{t('common.form.cancel')}
							</Button>

							<Button
								color="teal"
								type="submit"
								variant="light"
								loading={formik.isSubmitting}
							>
								{t('common.form.save')}
							</Button>
						</Flex>
					</Stack>
				</Form>
			</FormikProvider>
		</Modal>
	);
}
