import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

import resources from './resources';

export const defaultNS = 'translation';

i18n.use(initReactI18next).init({
	lng: 'tr',
	resources,
	defaultNS,
	fallbackLng: 'tr',
	interpolation: {
		escapeValue: false,
	},
});

export default i18n;
