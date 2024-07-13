import 'i18next';

import { resources, defaultNS } from '../src/locales';

declare module 'i18next' {
	interface CustomTypeOptions {
		defaultNS: typeof defaultNS;
		resources: (typeof resources)['tr'];
	}
}
