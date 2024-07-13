import { PasswordInputProps, PasswordInput } from '@mantine/core';
import { useFormikContext } from 'formik';

interface PasswordFieldProps extends Omit<PasswordInputProps, 'name'> {
	name: string;
}

export default function PasswordField({
	name,
	...inputProps
}: PasswordFieldProps) {
	const { getFieldProps, getFieldMeta } = useFormikContext();
	const { error, touched } = getFieldMeta(name);

	return (
		<PasswordInput
			spellCheck={false}
			error={touched && error}
			{...inputProps}
			{...getFieldProps(name)}
		/>
	);
}
