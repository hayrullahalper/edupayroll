import {
	Flex,
	Stack,
	Button,
	Divider,
	TextInput,
	PasswordInput,
} from '@mantine/core';
import {
	IconLock,
	IconIdBadge2,
	IconUserBolt,
	IconUserScan,
	IconBuildingCommunity,
} from '@tabler/icons-react';
import { Form, FormikHelpers, FormikProvider, useFormik } from 'formik';

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
					<TextInput
						size="sm"
						maxLength={50}
						inputMode="text"
						placeholder="Adınız"
						error={formik.errors.firstName}
						leftSection={<IconUserScan size={16} stroke={1.5} />}
						{...formik.getFieldProps('firstName')}
					/>
					<TextInput
						size="sm"
						maxLength={50}
						inputMode="text"
						placeholder="Soyadınız"
						error={formik.errors.lastName}
						leftSection={<IconUserScan size={16} stroke={1.5} />}
						{...formik.getFieldProps('lastName')}
					/>
					<TextInput
						size="sm"
						maxLength={50}
						inputMode="text"
						placeholder="Ünvanınız"
						error={formik.errors.title}
						leftSection={<IconIdBadge2 size={16} stroke={1.5} />}
						{...formik.getFieldProps('title')}
					/>

					<Divider />

					<TextInput
						size="sm"
						maxLength={50}
						inputMode="text"
						placeholder="Okulunuzun Adı"
						error={formik.errors.schoolName}
						leftSection={<IconBuildingCommunity size={16} stroke={1.5} />}
						{...formik.getFieldProps('schoolName')}
					/>

					<TextInput
						size="sm"
						maxLength={50}
						inputMode="text"
						placeholder="Okul Müdürünüzün Adı"
						error={formik.errors.principalName}
						leftSection={<IconUserBolt size={16} stroke={1.5} />}
						{...formik.getFieldProps('principalName')}
					/>

					<Divider />

					<PasswordInput
						size="sm"
						maxLength={32}
						placeholder="Şifreniz"
						error={formik.errors.password}
						leftSection={<IconLock size={16} stroke={1.5} />}
						{...formik.getFieldProps('password')}
					/>

					<Flex justify="flex-end">
						<Button
							type="submit"
							color="indigo"
							variant="light"
							loading={loading}
						>
							Kayıt Ol
						</Button>
					</Flex>
				</Stack>
			</Form>
		</FormikProvider>
	);
}
