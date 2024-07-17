import {
	IconBooks,
	IconUsers,
	IconSettings,
	IconTableExport,
	TablerIconsProps,
} from '@tabler/icons-react';
import { ReactNode } from 'react';

import { i18n } from '../../../locales';
import paths from '../../../routes/paths';

type NavLink = {
	to: string;
	icon: (props: TablerIconsProps) => ReactNode;
	label: string;
	relations?: string[];
};

export const links: NavLink[] = [
	{
		icon: IconBooks,
		to: paths.documents,
		relations: [paths.document],
		label: i18n.t('layout.sidebar.links.documents'),
	},
	{
		icon: IconUsers,
		to: paths.teachers,
		label: i18n.t('layout.sidebar.links.teachers'),
	},
	{
		to: paths.exports,
		icon: IconTableExport,
		label: i18n.t('layout.sidebar.links.exports'),
	},
	{
		to: paths.settings,
		icon: IconSettings,
		label: i18n.t('layout.sidebar.links.settings'),
		relations: [paths.school, paths.changePassword],
	},
];
