import user from './tr/user.json';
import auth from './tr/auth.json';
import layout from './tr/layout.json';
import common from './tr/common.json';
import teachers from './tr/teachers.json';

// eslint-disable-next-line import/no-anonymous-default-export
export default {
	tr: {
		translation: {
			auth,
			user,
			common,
			layout,
			teachers,
		} as const,
	},
} as const;
