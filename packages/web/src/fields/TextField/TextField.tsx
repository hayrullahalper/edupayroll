import { forwardRef } from 'react';
import { useFormikContext } from 'formik';
import { TextInput, TextInputProps } from '@mantine/core';

interface TextFieldProps extends Omit<TextInputProps, 'name'> {
	name: string;
}

const TextField = forwardRef<HTMLInputElement, TextFieldProps>(
	({ name, ...inputProps }, ref) => {
		const { getFieldProps, getFieldMeta } = useFormikContext();
		const { error, touched } = getFieldMeta(name);

		return (
			<TextInput
				ref={ref}
				spellCheck={false}
				autoComplete="off"
				error={touched && error}
				{...inputProps}
				{...getFieldProps(name)}
			/>
		);
	},
);

TextField.displayName = 'TextField';

export default TextField;
