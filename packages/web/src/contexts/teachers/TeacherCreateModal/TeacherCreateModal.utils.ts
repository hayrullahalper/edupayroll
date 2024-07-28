import { object, ObjectSchema, string } from 'yup';

import { i18n } from '../../../locales';

export interface TeacherCreateFormInput {
	name: string;
	branch: string;
	idNumber: string;
	description: string;
}

export const teacherCreateFormSchema: ObjectSchema<TeacherCreateFormInput> =
	object({
		branch: string()
			.required(i18n.t('teachers.createTeacher.form.branch.required'))
			.min(3, i18n.t('teachers.createTeacher.form.branch.minLength'))
			.max(50, i18n.t('teachers.createTeacher.form.branch.maxLength')),
		name: string()
			.required(i18n.t('teachers.createTeacher.form.name.required'))
			.min(3, i18n.t('teachers.createTeacher.form.name.minLength'))
			.max(50, i18n.t('teachers.createTeacher.form.name.maxLength')),
		idNumber: string()
			.required(i18n.t('teachers.createTeacher.form.idNumber.required'))
			.min(11, i18n.t('teachers.createTeacher.form.idNumber.minLength'))
			.max(11, i18n.t('teachers.createTeacher.form.idNumber.maxLength')),
		description: string()
			.required(i18n.t('teachers.createTeacher.form.description.required'))
			.min(3, i18n.t('teachers.createTeacher.form.description.minLength'))
			.max(255, i18n.t('teachers.createTeacher.form.description.maxLength')),
	});

export const teacherCreateFormInitialValues: TeacherCreateFormInput = {
	name: '',
	branch: '',
	idNumber: '',
	description: '',
};
