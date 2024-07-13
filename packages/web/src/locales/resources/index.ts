import auth from './tr/auth.json';
import layout from './tr/layout.json';
import common from './tr/common.json';

// eslint-disable-next-line import/no-anonymous-default-export
export default {
	tr: {
		translation: {
			auth,
			common,
			layout,
		} as const,
	},
} as const;
