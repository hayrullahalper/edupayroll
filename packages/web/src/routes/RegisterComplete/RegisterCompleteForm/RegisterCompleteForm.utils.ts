import { object, ObjectSchema, string } from 'yup';

export interface RegisterCompleteFormInput {
	title: string;
	password: string;
	lastName: string;
	firstName: string;
	schoolName: string;
	principalName: string;
}

export const registerCompleteFormSchema: ObjectSchema<RegisterCompleteFormInput> =
	object({
		firstName: string()
			.required('Lütfen adınızı giriniz.')
			.min(3, 'Lütfen en az 3 karakter giriniz.')
			.max(50, 'Adınız en fazla 50 karakter olabilir.'),
		lastName: string()
			.required('Lütfen soyadınızı giriniz.')
			.min(3, 'Lütfen en az 3 karakter giriniz.')
			.max(50, 'Soyadınız en fazla 50 karakter olabilir.'),
		title: string()
			.required('Lütfen ünvanınızı giriniz.')
			.min(3, 'Lütfen en az 3 karakter giriniz.')
			.max(50, 'Ünvanınız en fazla 50 karakter olabilir.'),
		password: string()
			.required('Lütfen şifrenizi giriniz.')
			.min(6, 'Lütfen en az 6 karakter giriniz.')
			.max(32, 'Şifreniz en fazla 32 karakter olabilir.')
			.matches(
				/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).*$/,
				'Şifreniz en az bir büyük harf, bir küçük harf ve bir rakam içermelidir.',
			),
		schoolName: string()
			.required('Lütfen okul adını giriniz.')
			.min(3, 'Lütfen en az 3 karakter giriniz.')
			.max(50, 'Okul adı en fazla 50 karakter olabilir.'),
		principalName: string()
			.required('Lütfen okul müdürünüzün adını giriniz.')
			.min(3, 'Lütfen en az 3 karakter giriniz.')
			.max(50, 'Okul müdürünüzün adı en fazla 50 karakter olabilir.'),
	});

export const registerCompleteFormInitialValues: RegisterCompleteFormInput = {
	title: '',
	password: '',
	lastName: '',
	firstName: '',
	schoolName: '',
	principalName: '',
};
