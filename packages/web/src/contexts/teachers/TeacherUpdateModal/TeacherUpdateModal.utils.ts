import type { ObjectSchema } from 'yup';
import { object, string } from 'yup';

import { i18n } from '../../../locales';

export interface TeacherUpdateFormInput {
	name: string;
	branch: string;
	idNumber: string;
	description: string;
}

export const teacherUpdateFormSchema: ObjectSchema<TeacherUpdateFormInput>
	= object({
		name: string()
			.required(i18n.t('teachers.updateTeacher.form.name.required'))
			.min(3, i18n.t('teachers.updateTeacher.form.name.minLength'))
			.max(50, i18n.t('teachers.updateTeacher.form.name.maxLength')),
		branch: string()
			.required(i18n.t('teachers.updateTeacher.form.branch.required'))
			.min(3, i18n.t('teachers.updateTeacher.form.branch.minLength'))
			.max(50, i18n.t('teachers.updateTeacher.form.branch.maxLength')),
		description: string()
			.required(i18n.t('teachers.updateTeacher.form.description.required'))
			.min(3, i18n.t('teachers.updateTeacher.form.description.minLength'))
			.max(100, i18n.t('teachers.updateTeacher.form.description.maxLength')),
		idNumber: string()
			.required(i18n.t('teachers.updateTeacher.form.idNumber.required'))
			.min(11, i18n.t('teachers.updateTeacher.form.idNumber.minLength'))
			.max(11, i18n.t('teachers.updateTeacher.form.idNumber.maxLength')),
	});

export const teacherUpdateFormInitialValues: TeacherUpdateFormInput = {
	name: '',
	branch: '',
	idNumber: '',
	description: '',
};
