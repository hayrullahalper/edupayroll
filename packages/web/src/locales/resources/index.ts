import auth from './tr/auth.json';
import common from './tr/common.json';
import components from './tr/components.json';
import layout from './tr/layout.json';
import teachers from './tr/teachers.json';
import user from './tr/user.json';
import documents from './tr/documents.json';

export default {
	tr: {
		translation: {
			auth,
			user,
			common,
			layout,
			teachers,
			documents,
			components,
		} as const,
	},
} as const;
