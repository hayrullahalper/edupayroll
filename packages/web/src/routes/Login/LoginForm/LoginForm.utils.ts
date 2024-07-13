import { boolean, object, ObjectSchema, string } from 'yup';

import { i18n } from '../../../locales';

export interface LoginFormInput {
	email: string;
	password: string;
	remember?: boolean;
}

export const loginFormSchema: ObjectSchema<LoginFormInput> = object({
	email: string()
		.email(i18n.t('auth.login.form.email.invalid'))
		.required(i18n.t('auth.login.form.email.required')),
	password: string().required(i18n.t('auth.login.form.password.required')),
	remember: boolean(),
});

export const loginFormInitialValues: LoginFormInput = {
	email: '',
	password: '',
};
