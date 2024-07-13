import { TextInput, TextInputProps } from '@mantine/core';
import { useFormikContext } from 'formik';

interface TextFieldProps extends Omit<TextInputProps, 'name'> {
	name: string;
}

export default function TextField({ name, ...inputProps }: TextFieldProps) {
	const { getFieldProps, getFieldMeta } = useFormikContext();
	const { error, touched } = getFieldMeta(name);

	return (
		<TextInput
			spellCheck={false}
			autoComplete="off"
			error={touched && error}
			{...inputProps}
			{...getFieldProps(name)}
		/>
	);
}
