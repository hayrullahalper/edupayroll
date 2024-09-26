import type { ObjectSchema } from 'yup';
import { object, string } from 'yup';

import { i18n } from '../../../../locales';

export interface ProfileEmailUpdateModalFormInput {
	password: string;
}

export const profileEmailUpdateModalFormSchema: ObjectSchema<ProfileEmailUpdateModalFormInput>
	= object({
		password: string().required(
			i18n.t('user.updateEmail.modal.password.required'),
		),
	});

export const profileEmailUpdateModalFormInitialValues: ProfileEmailUpdateModalFormInput
	= {
		password: '',
	};
