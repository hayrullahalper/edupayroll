import {
	IconSchool,
	IconIdBadge2,
	IconUserScan,
	IconSquareRounded,
} from '@tabler/icons-react';
import { MouseEvent } from 'react';
import { useTranslation } from 'react-i18next';
import { Form, FormikHelpers, FormikProvider, useFormik } from 'formik';
import { Button, Divider, Flex, Modal, Stack, Text } from '@mantine/core';

import TextField from '../../../fields/TextField';

import {
	TeacherCreateFormInput,
	teacherCreateFormSchema,
	teacherCreateFormInitialValues,
} from './TeacherCreateModal.utils';

interface TeacherCreateModalProps {
	onClose: () => void;
	onSubmit: (
		values: TeacherCreateFormInput,
		helpers: FormikHelpers<TeacherCreateFormInput>,
	) => Promise<void>;
}

export default function TeacherCreateModal({
	onClose,
	onSubmit,
}: TeacherCreateModalProps) {
	const { t } = useTranslation();

	const formik = useFormik({
		onSubmit,
		validateOnBlur: true,
		validateOnChange: false,
		validationSchema: teacherCreateFormSchema,
		initialValues: teacherCreateFormInitialValues,
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
			title={t('teachers.createTeacher.title')}
		>
			<FormikProvider value={formik}>
				<Form noValidate onSubmit={formik.handleSubmit}>
					<Stack gap="sm" pt="sm">
						<TextField
							size="sm"
							name="name"
							maxLength={50}
							rightSection={<IconUserScan size={16} stroke={1.5} />}
							placeholder={t('teachers.createTeacher.form.name.placeholder')}
							label={
								<Text fw="200">
									{t('teachers.createTeacher.form.name.label')}
								</Text>
							}
						/>

						<TextField
							size="sm"
							name="branch"
							maxLength={50}
							rightSection={<IconSchool size={16} stroke={1.5} />}
							label={
								<Text fw="200">
									{t('teachers.createTeacher.form.branch.label')}
								</Text>
							}
							placeholder={t('teachers.createTeacher.form.branch.placeholder')}
						/>

						<TextField
							size="sm"
							name="description"
							maxLength={50}
							rightSection={<IconSquareRounded size={16} stroke={1.5} />}
							label={
								<Text fw="200">
									{t('teachers.createTeacher.form.description.label')}
								</Text>
							}
							placeholder={t(
								'teachers.createTeacher.form.description.placeholder',
							)}
						/>

						<TextField
							size="sm"
							maxLength={11}
							name="idNumber"
							inputMode="numeric"
							rightSection={<IconIdBadge2 size={16} stroke={1.5} />}
							label={
								<Text fw="200">
									{t('teachers.createTeacher.form.idNumber.label')}
								</Text>
							}
							placeholder={t(
								'teachers.createTeacher.form.idNumber.placeholder',
							)}
						/>

						<Divider />

						<Flex gap="sm" justify="flex-end">
							<Button
								color="red"
								type="button"
								variant="subtle"
								onClick={handleCancel}
								disabled={formik.isSubmitting}
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
