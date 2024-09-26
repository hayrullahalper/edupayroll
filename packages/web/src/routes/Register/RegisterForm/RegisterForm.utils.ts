import type { ObjectSchema } from 'yup';
import { object, string } from 'yup';

import { i18n } from '../../../locales';

export interface RegisterFormInput {
	email: string;
}

export const registerFormSchema: ObjectSchema<RegisterFormInput> = object({
	email: string()
		.email(i18n.t('auth.register.form.email.invalid'))
		.required(i18n.t('auth.register.form.email.required')),
});

export const registerFormInitialValues: RegisterFormInput = {
	email: '',
};
