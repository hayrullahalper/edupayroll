import { boolean, object, ObjectSchema, string } from 'yup';

export interface LoginFormInput {
	email: string;
	password: string;
	remember?: boolean;
}

export const loginFormSchema: ObjectSchema<LoginFormInput> = object({
	email: string()
		.email('Lütfen geçerli bir e-posta adresi giriniz.')
		.required('Lütfen e-posta adresinizi giriniz.'),
	password: string().required('Lütfen şifrenizi giriniz.'),
	remember: boolean(),
});

export const loginFormInitialValues: LoginFormInput = {
	email: '',
	password: '',
};
