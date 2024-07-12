import { object, ObjectSchema, string } from 'yup';

export interface RegisterFormInput {
	email: string;
}

export const registerFormSchema: ObjectSchema<RegisterFormInput> = object({
	email: string()
		.email('Lütfen geçerli bir e-posta adresi giriniz.')
		.required('Lütfen e-posta adresinizi giriniz.'),
});

export const registerFormInitialValues: RegisterFormInput = {
	email: '',
};
