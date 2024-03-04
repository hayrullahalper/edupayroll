export default {
	document: '/documents/:documentId',
	documents: '/documents',

	teacher: '/teachers/:teacherId',
	teachers: '/teachers',

	exports: '/exports',

	login: '/login',
	logout: '/logout',
	register: '/register',
	registerComplete: '/register/complete',
	resetPassword: '/reset-password',
	forgotPassword: '/forgot-password',

	school: '/settings/school',
	profile: '/settings/profile',
	settings: '/settings',
	changePassword: '/settings/change-password',
} as const;
