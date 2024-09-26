import type { ObjectSchema } from 'yup';
import { object, string } from 'yup';

import { i18n } from '../../../locales';

export interface ResetPasswordFormInput {
	email: string;
}

export const resetPasswordFormSchema: ObjectSchema<ResetPasswordFormInput>
	= object({
		email: string()
			.email(i18n.t('auth.resetPassword.form.email.invalid'))
			.required(i18n.t('auth.resetPassword.form.email.required')),
	});

export const resetPasswordFormInitialValues: ResetPasswordFormInput = {
	email: '',
};
