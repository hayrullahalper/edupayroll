import type { TextInputProps } from '@mantine/core';
import { TextInput } from '@mantine/core';
import { useFormikContext } from 'formik';
import { forwardRef } from 'react';

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
