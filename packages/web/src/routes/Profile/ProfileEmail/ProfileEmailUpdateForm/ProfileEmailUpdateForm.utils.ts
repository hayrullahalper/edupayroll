import type { ObjectSchema } from 'yup';
import { object, string } from 'yup';

import { i18n } from '../../../../locales';

export interface ProfileEmailUpdateFormInput {
	email: string;
}

export const profileEmailUpdateFormSchema: ObjectSchema<ProfileEmailUpdateFormInput>
	= object({
		email: string()
			.required(i18n.t('user.updateEmail.form.email.required'))
			.email(i18n.t('user.updateEmail.form.email.invalid')),
	});

export const profileEmailUpdateFormInitialValues: ProfileEmailUpdateFormInput
	= { email: '' };
