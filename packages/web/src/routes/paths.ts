export default {
	home: '/',

	document: '/documents/:documentId',
	documents: '/documents',

	teacher: '/teachers/:teacherId',
	teachers: '/teachers',

	exports: '/exports',

	login: '/login',
	logout: '/logout',
	register: '/register',
	resetPassword: '/reset-password',
	registerComplete: '/register/complete',
	resetPasswordComplete: '/reset-password/complete',

	school: '/settings/school',
	profile: '/settings/profile',
	settings: '/settings',
	changePassword: '/settings/change-password',
} as const;
