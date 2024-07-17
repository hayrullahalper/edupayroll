export default {
	home: '/',

	document: '/documents/:documentId',
	documents: '/documents',

	exports: '/exports',
	teachers: '/teachers',

	login: '/login',
	logout: '/logout',
	register: '/register',
	resetPassword: '/reset-password',
	registerComplete: '/register/complete',
	resetPasswordComplete: '/reset-password/complete',

	school: '/settings/school',
	settings: '/settings',
	changePassword: '/settings/change-password',

	privacyPolicy: '/privacy-policy',
	termsOfService: '/terms-of-service',
} as const;
