import type { ObjectSchema } from 'yup';
import { object, string } from 'yup';

import { i18n } from '../../locales';

export interface UpdatePasswordFormInput {
	newPassword: string;
	currentPassword: string;
	confirmPassword: string;
}

export const updatePasswordFormSchema: ObjectSchema<UpdatePasswordFormInput>
	= object({
		currentPassword: string().required(
			i18n.t('user.updatePassword.form.currentPassword.required'),
		),
		confirmPassword: string()
			.required(i18n.t('user.updatePassword.form.confirmPassword.required'))
			.test(
				'passwords-match',
				i18n.t('user.updatePassword.form.confirmPassword.invalid'),
				(value, context) => value === context.parent.newPassword,
			),
		newPassword: string()
			.required(i18n.t('user.updatePassword.form.newPassword.required'))
			.min(6, i18n.t('user.updatePassword.form.newPassword.minLength'))
			.max(32, i18n.t('user.updatePassword.form.newPassword.maxLength'))
			.matches(
				/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$/,
				i18n.t('user.updatePassword.form.newPassword.invalid'),
			),
	});

export const updatePasswordFormInitialValues: UpdatePasswordFormInput = {
	newPassword: '',
	currentPassword: '',
	confirmPassword: '',
};
