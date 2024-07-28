import {
	IconSchool,
	IconIdBadge2,
	IconUserScan,
	IconInfoSquareRounded,
} from '@tabler/icons-react';
import { MouseEvent } from 'react';
import { useTranslation } from 'react-i18next';
import { Form, FormikHelpers, FormikProvider, useFormik } from 'formik';
import { Button, Divider, Flex, Modal, Stack, Text } from '@mantine/core';

import { Teacher } from '../../../api';
import TextField from '../../../fields/TextField';

import {
	TeacherUpdateFormInput,
	teacherUpdateFormSchema,
	teacherUpdateFormInitialValues,
} from './TeacherUpdateModal.utils';

interface TeacherUpdateModalProps {
	teacher: Omit<Teacher, 'id'>;
	onClose: () => void;
	onSubmit: (
		values: TeacherUpdateFormInput,
		helpers: FormikHelpers<TeacherUpdateFormInput>,
	) => Promise<void>;
}

export default function TeacherUpdateModal({
	teacher,
	onClose,
	onSubmit,
}: TeacherUpdateModalProps) {
	const { t } = useTranslation();

	const formik = useFormik({
		onSubmit,
		validateOnBlur: true,
		validateOnChange: false,
		validationSchema: teacherUpdateFormSchema,
		initialValues: {
			...teacherUpdateFormInitialValues,
			...teacher,
		},
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
			title={t('teachers.updateTeacher.title')}
		>
			<FormikProvider value={formik}>
				<Form noValidate onSubmit={formik.handleSubmit}>
					<Stack gap="sm" pt="sm">
						<TextField
							size="sm"
							name="name"
							maxLength={50}
							rightSection={<IconUserScan size={16} stroke={1.5} />}
							placeholder={t('teachers.updateTeacher.form.name.placeholder')}
							label={
								<Text fw="200">
									{t('teachers.updateTeacher.form.name.label')}
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
									{t('teachers.updateTeacher.form.branch.label')}
								</Text>
							}
							placeholder={t('teachers.updateTeacher.form.branch.placeholder')}
						/>

						<TextField
							size="sm"
							name="description"
							maxLength={50}
							rightSection={<IconInfoSquareRounded size={16} stroke={1.5} />}
							label={
								<Text fw="200">
									{t('teachers.updateTeacher.form.description.label')}
								</Text>
							}
							placeholder={t(
								'teachers.updateTeacher.form.description.placeholder',
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
									{t('teachers.updateTeacher.form.idNumber.label')}
								</Text>
							}
							placeholder={t(
								'teachers.updateTeacher.form.idNumber.placeholder',
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
								{t('common.form.update')}
							</Button>
						</Flex>
					</Stack>
				</Form>
			</FormikProvider>
		</Modal>
	);
}
