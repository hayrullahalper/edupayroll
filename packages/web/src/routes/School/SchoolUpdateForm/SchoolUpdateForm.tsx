import {
	IconIdBadge2,
	IconUserBolt,
	IconUserEdit,
	IconBuildingCommunity,
} from '@tabler/icons-react';
import { MouseEvent, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { Button, Flex, Stack, Text } from '@mantine/core';
import { Form, FormikHelpers, FormikProvider, useFormik } from 'formik';

import { School } from '../../../api';
import TextField from '../../../fields/TextField';
import useInputMode from '../../../hooks/useInputMode';

import {
	SchoolUpdateFormInput,
	schoolUpdateFormSchema,
	schoolUpdateFormInitialValues,
} from './SchoolUpdateForm.utils';

interface SchoolUpdateFormProps {
	school?: Omit<School, 'id'>;
	onSubmit: (
		values: SchoolUpdateFormInput,
		helpers: FormikHelpers<SchoolUpdateFormInput>,
	) => Promise<void>;
}

export default function SchoolUpdateForm({
	school,
	onSubmit,
}: SchoolUpdateFormProps) {
	const { t } = useTranslation();
	const [mode, setMode, inputProps] = useInputMode();

	const inputRef = useRef<HTMLInputElement>(null);

	const formik = useFormik<SchoolUpdateFormInput>({
		validateOnBlur: true,
		validateOnChange: false,
		validationSchema: schoolUpdateFormSchema,
		onSubmit: async (values, helpers) => {
			await onSubmit(values, helpers);
			setMode('view');
		},
		initialValues: {
			...schoolUpdateFormInitialValues,
			...school,
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
						name="name"
						ref={inputRef}
						description={t('user.updateSchool.form.name.description')}
						rightSection={<IconBuildingCommunity size={18} stroke={1.5} />}
						label={
							<Text fw="200">{t('user.updateSchool.form.name.label')}</Text>
						}
						{...inputProps}
					/>

					<TextField
						name="principalName"
						rightSection={<IconUserBolt size={18} stroke={1.5} />}
						description={t('user.updateSchool.form.principalName.description')}
						label={
							<Text fw="200">
								{t('user.updateSchool.form.principalName.label')}
							</Text>
						}
						{...inputProps}
					/>

					<TextField
						name="editorName"
						rightSection={<IconUserEdit size={18} stroke={1.5} />}
						description={t('user.updateSchool.form.editorName.description')}
						label={
							<Text fw="200">
								{t('user.updateSchool.form.editorName.label')}
							</Text>
						}
						{...inputProps}
					/>

					<TextField
						name="editorTitle"
						rightSection={<IconIdBadge2 size={18} stroke={1.5} />}
						description={t('user.updateSchool.form.editorTitle.description')}
						label={
							<Text fw="200">
								{t('user.updateSchool.form.editorTitle.label')}
							</Text>
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
