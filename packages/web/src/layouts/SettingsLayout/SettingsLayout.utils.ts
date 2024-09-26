import { i18n } from '../../locales';
import paths from '../../routes/paths';

interface SettingLink {
	to: string;
	label: string;
	description: string;
}

export const links: SettingLink[] = [
	{
		to: paths.settings,
		label: i18n.t('layout.settings.user.title'),
		description: i18n.t('layout.settings.user.description'),
	},
	{
		to: paths.changePassword,
		label: i18n.t('layout.settings.password.title'),
		description: i18n.t('layout.settings.password.description'),
	},
	{
		to: paths.school,
		label: i18n.t('layout.settings.school.title'),
		description: i18n.t('layout.settings.school.description'),
	},
];
