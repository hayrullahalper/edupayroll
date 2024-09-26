import type { ObjectSchema } from 'yup';
import { object, string } from 'yup';

import { i18n } from '../../../locales';

export interface RegisterCompleteFormInput {
	title: string;
	password: string;
	lastName: string;
	firstName: string;
	schoolName: string;
	principalName: string;
}

export const registerCompleteFormSchema: ObjectSchema<RegisterCompleteFormInput>
	= object({
		firstName: string()
			.required(i18n.t('auth.registerComplete.form.firstName.required'))
			.min(3, i18n.t('auth.registerComplete.form.firstName.minLength'))
			.max(50, i18n.t('auth.registerComplete.form.firstName.maxLength')),
		lastName: string()
			.required(i18n.t('auth.registerComplete.form.lastName.required'))
			.min(3, i18n.t('auth.registerComplete.form.lastName.minLength'))
			.max(50, i18n.t('auth.registerComplete.form.lastName.maxLength')),
		title: string()
			.required(i18n.t('auth.registerComplete.form.title.required'))
			.min(3, i18n.t('auth.registerComplete.form.title.minLength'))
			.max(50, i18n.t('auth.registerComplete.form.title.maxLength')),
		password: string()
			.required(i18n.t('auth.registerComplete.form.password.required'))
			.min(6, i18n.t('auth.registerComplete.form.password.minLength'))
			.max(32, i18n.t('auth.registerComplete.form.password.maxLength'))
			.matches(
				/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$/,
				i18n.t('auth.registerComplete.form.password.invalid'),
			),
		schoolName: string()
			.required(i18n.t('auth.registerComplete.form.schoolName.required'))
			.min(3, i18n.t('auth.registerComplete.form.schoolName.minLength'))
			.max(50, i18n.t('auth.registerComplete.form.schoolName.maxLength')),
		principalName: string()
			.required(i18n.t('auth.registerComplete.form.principalName.required'))
			.min(3, i18n.t('auth.registerComplete.form.principalName.minLength'))
			.max(50, i18n.t('auth.registerComplete.form.principalName.maxLength')),
	});

export const registerCompleteFormInitialValues: RegisterCompleteFormInput = {
	title: '',
	password: '',
	lastName: '',
	firstName: '',
	schoolName: '',
	principalName: '',
};
