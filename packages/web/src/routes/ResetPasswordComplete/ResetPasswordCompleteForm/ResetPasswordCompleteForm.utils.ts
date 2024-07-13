import { object, ObjectSchema, string } from 'yup';

import { i18n } from '../../../locales';

export interface ResetPasswordCompleteFormInput {
	password: string;
}

export const resetPasswordCompleteFormSchema: ObjectSchema<ResetPasswordCompleteFormInput> =
	object({
		password: string()
			.required(i18n.t('auth.resetPasswordComplete.form.password.required'))
			.min(6, i18n.t('auth.resetPasswordComplete.form.password.minLength'))
			.max(32, i18n.t('auth.resetPasswordComplete.form.password.maxLength'))
			.matches(
				/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).*$/,
				i18n.t('auth.resetPasswordComplete.form.password.invalid'),
			),
	});

export const resetPasswordCompleteFormInitialValues: ResetPasswordCompleteFormInput =
	{ password: '' };
