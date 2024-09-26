import type { ObjectSchema } from 'yup';
import { object, string } from 'yup';

import { i18n } from '../../../../locales';

export interface ProfileNameUpdateFormInput {
	lastName: string;
	firstName: string;
}

export const profileNameUpdateFormSchema: ObjectSchema<ProfileNameUpdateFormInput>
	= object({
		lastName: string()
			.required(i18n.t('user.updateName.form.lastName.required'))
			.min(3, i18n.t('user.updateName.form.lastName.minLength'))
			.max(50, i18n.t('user.updateName.form.lastName.maxLength')),
		firstName: string()
			.required(i18n.t('user.updateName.form.firstName.required'))
			.min(3, i18n.t('user.updateName.form.firstName.minLength'))
			.max(50, i18n.t('user.updateName.form.firstName.maxLength')),
	});

export const profileNameUpdateFormInitialValues: ProfileNameUpdateFormInput = {
	lastName: '',
	firstName: '',
};
