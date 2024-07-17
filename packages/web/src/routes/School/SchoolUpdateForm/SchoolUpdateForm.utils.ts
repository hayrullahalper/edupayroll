import { object, ObjectSchema, string } from 'yup';

import { i18n } from '../../../locales';

export interface SchoolUpdateFormInput {
	name: string;
	editorName: string;
	editorTitle: string;
	principalName: string;
}

export const schoolUpdateFormSchema: ObjectSchema<SchoolUpdateFormInput> =
	object({
		name: string()
			.required(i18n.t('user.updateSchool.form.name.required'))
			.min(3, i18n.t('user.updateSchool.form.name.minLength'))
			.max(100, i18n.t('user.updateSchool.form.name.maxLength')),
		editorName: string()
			.required(i18n.t('user.updateSchool.form.editorName.required'))
			.min(3, i18n.t('user.updateSchool.form.editorName.minLength'))
			.max(50, i18n.t('user.updateSchool.form.editorName.maxLength')),
		editorTitle: string()
			.required(i18n.t('user.updateSchool.form.editorTitle.required'))
			.min(3, i18n.t('user.updateSchool.form.editorTitle.minLength'))
			.max(50, i18n.t('user.updateSchool.form.editorTitle.maxLength')),
		principalName: string()
			.required(i18n.t('user.updateSchool.form.principalName.required'))
			.min(3, i18n.t('user.updateSchool.form.principalName.minLength'))
			.max(50, i18n.t('user.updateSchool.form.principalName.maxLength')),
	});

export const schoolUpdateFormInitialValues: SchoolUpdateFormInput = {
	name: '',
	editorName: '',
	editorTitle: '',
	principalName: '',
};
